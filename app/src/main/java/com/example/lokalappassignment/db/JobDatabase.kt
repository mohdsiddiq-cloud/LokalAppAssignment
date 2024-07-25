package com.example.lokalappassignment.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lokalappassignment.models.SavedJobResult


@Database(entities = [SavedJobResult::class], version = 1)
abstract class JobDatabase : RoomDatabase() {
    abstract fun jobsDao(): JobDao
    companion object{
        private var INSTANCE: JobDatabase?=null
        fun getDatabase(context: Context) : JobDatabase {
            synchronized(this){
                if(INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(context, JobDatabase::class.java,"jobDB").build()
                }
            }
            return INSTANCE!!;
        }
    }
}