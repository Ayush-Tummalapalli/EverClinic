
/**
 * This file defines the Treatment data model.
 */
package com.example.everclinic.data.model

data class Treatment(
    val id: String,
    val patientId: String,
    val appointmentId: String,
    val doctorId: String,
    val date: String,
    val diagnosis: String,
    val treatment: String,
    val prescription: String,
    val followUpDate: String?,
    val notes: String
)
