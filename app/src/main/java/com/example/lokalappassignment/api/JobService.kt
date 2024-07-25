package com.example.lokalappassignment.api
import com.example.lokalappassignment.models.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobService {

    @GET("jobs")
    suspend fun getJobs(@Query("page") page: Int): Response<JobResponse>

}