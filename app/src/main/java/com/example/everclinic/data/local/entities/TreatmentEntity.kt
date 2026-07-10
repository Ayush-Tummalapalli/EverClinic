package com.example.everclinic.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "treatments")
data class TreatmentEntity(
    @PrimaryKey val id: String,
    val patientId: String,
    val doctorId: String,
    val date: String,
    val diagnosis: String,
    val description: String,
    val prescription: String,
    val followUpDate: String?,
    val notes: String?,
    val status: String
)
