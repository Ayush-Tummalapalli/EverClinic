package com.example.everclinic.data.local.relations

import androidx.room.Embedded
import com.example.everclinic.data.local.entities.TreatmentEntity

data class TreatmentDetails(
    @Embedded val treatment: TreatmentEntity,
    val patientName: String,
    val doctorName: String
)
