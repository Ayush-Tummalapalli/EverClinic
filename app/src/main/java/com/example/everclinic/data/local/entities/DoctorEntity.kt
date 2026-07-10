package com.example.everclinic.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class DoctorEntity(
    @PrimaryKey val id: String,
    val name: String,
    val specialty: String,
    val experience: Int,
    val phone: String,
    val email: String,
    val availability: String // JSON string for list of days
)
