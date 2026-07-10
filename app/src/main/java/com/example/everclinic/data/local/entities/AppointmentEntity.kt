package com.example.everclinic.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey val id: String,
    val patientId: String,
    val doctorId: String,
    val date: String,
    val time: String,
    val reason: String,
    val status: String,
    val notes: String
)
