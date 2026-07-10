package com.example.everclinic.data.local.relations

import androidx.room.Embedded
import com.example.everclinic.data.local.entities.AppointmentEntity

data class AppointmentDetails(
    @Embedded val appointment: AppointmentEntity,
    val patientName: String,
    val doctorName: String
)
