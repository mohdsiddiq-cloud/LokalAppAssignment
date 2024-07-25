package com.example.lokalappassignment.applications

import android.app.Application
import com.example.lokalappassignment.api.JobService
import com.example.lokalappassignment.api.RetrofitHelper
import com.example.lokalappassignment.db.JobDatabase
import com.example.lokalappassignment.repository.JobRepository



class JobApplication() : Application() {
    lateinit var repository: JobRepository

    override fun onCreate() {
        super.onCreate()
        initialize();
    }
    private fun initialize(){
        val service = RetrofitHelper.getInstance().create(JobService::class.java)
        val database = JobDatabase.getDatabase(applicationContext)

        repository = JobRepository(service,database,applicationContext)
    }
}