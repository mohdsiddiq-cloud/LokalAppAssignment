package com.example.lokalappassignment.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lokalappassignment.api.JobService
import com.example.lokalappassignment.db.JobDatabase
import com.example.lokalappassignment.models.JobResponse
import com.example.lokalappassignment.models.Result
import com.example.lokalappassignment.models.SavedJobResult
import com.example.lokalappassignment.utils.NetworkUtils


class JobRepository(
    private val jobService: JobService,
    private val jobDatabase: JobDatabase,
    private val applicationContext: Context,
) {
    private var jobLiveData = MutableLiveData<JobResponse>()
    private var jobDetailLiveData = MutableLiveData<Result>()

    val jobs: LiveData<JobResponse> get() = jobLiveData
    val jobDetail: LiveData<Result> get() = jobDetailLiveData

    val saveJobMutable = MutableLiveData<List<SavedJobResult>>()
    val saveJobs: LiveData<List<SavedJobResult>> get() = saveJobMutable

    suspend fun getJobs() {
        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {
                val result = jobService.getJobs(1)
                if (result.isSuccessful) {
                    Log.d("JobRepository", "API Response: ${result.body()}")
                    jobLiveData.postValue(result.body())
                } else {
                    Log.e("JobRepository", "API Error: ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("JobRepository", "Exception: ${e.message}")
            }
        } else {
            Log.e("JobRepository", "No internet connection")
        }
    }


    suspend fun saveJob(job: SavedJobResult): Boolean {
        val existingJob = jobDatabase.jobsDao().getJobById(job.id)
        return if (existingJob == null) {
            jobDatabase.jobsDao().addJob(job)
            true // Job added successfully
        } else {
            Log.d("JobRepository", "Job with ID ${job.id} already exists.")
            false // Job already exists
        }
    }

    suspend fun deleteJob(jobId: Int) {
        jobDatabase.jobsDao().deleteJobById(jobId)
        getSavedJobs()
    }

    suspend fun getSavedJobs(){
        var jobs= jobDatabase.jobsDao().getJob()
        saveJobMutable.postValue(jobs)
    }
    suspend fun getJobById(jobId: Int) {
        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {

                val result = jobService.getJobs(1)
                if (result.isSuccessful) {
                    val job = result.body()?.results?.find { it.id == jobId }
                    if (job != null) {
                        jobDetailLiveData.postValue(job!!)
                        Log.d("checkSave","here");
                    } else {
                        Log.e("JobRepository", "Job with ID $jobId not found")
                    }
                } else {
                    Log.e("JobRepository", "API Error: ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("JobRepository", "Exception: ${e.message}")
            }
        } else {
            Log.e("JobRepository", "No internet connection")
        }
    }


}