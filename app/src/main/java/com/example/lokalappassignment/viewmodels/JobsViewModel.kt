package com.example.lokalappassignment.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lokalappassignment.repository.JobRepository
import com.example.lokalappassignment.models.JobResponse
import com.example.lokalappassignment.models.Result
import com.example.lokalappassignment.models.SavedJobResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobsViewModel(private val repository: JobRepository) : ViewModel() {

    val jobs: LiveData<JobResponse> = repository.jobs
    val jobDetail: LiveData<Result> = repository.jobDetail
    val savedJobs: LiveData<List<SavedJobResult>> = repository.saveJobs

    private val _jobAddStatus = MutableLiveData<String>()
    val jobAddStatus: LiveData<String> get() = _jobAddStatus

    fun fetchJobs() {
        viewModelScope.launch {
            try {
                repository.getJobs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSavedJobs() {
        viewModelScope.launch {
            try {
                repository.getSavedJobs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchJobById(jobId: Int) {
        viewModelScope.launch {
            try {
                repository.getJobById(jobId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addJob(id: Int, title: String, location: String, salary: String, phone: String) {
        val newJob = SavedJobResult(id = id, title = title, location = location, salary = salary, phone = phone)
        viewModelScope.launch {
            val isJobAdded = repository.saveJob(newJob)
            if (isJobAdded) {
                _jobAddStatus.postValue("Job added successfully!")
            } else {
                _jobAddStatus.postValue("Job already exists!")
            }
        }
    }

    fun deleteJob(jobId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteJob(jobId)
        }
    }
}
