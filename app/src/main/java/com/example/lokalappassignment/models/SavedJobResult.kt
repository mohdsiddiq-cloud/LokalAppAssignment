package com.example.lokalappassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "job")
data class SavedJobResult(
    @PrimaryKey() val id: Int,
    val title: String,
    val location: String,
    val salary: String,
    val phone: String
    )
