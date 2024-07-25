package com.example.lokalappassignment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lokalappassignment.models.SavedJobResult


@Dao
interface JobDao {
    @Insert
    suspend fun addJob(job : SavedJobResult)

    @Query("Select * from job")
    suspend fun getJob() : List<SavedJobResult>

    @Query("DELETE FROM job WHERE id = :jobId")
    fun deleteJobById(jobId: Int)

    @Query("SELECT * FROM job WHERE id = :jobId")
    suspend fun getJobById(jobId: Int): SavedJobResult?

}