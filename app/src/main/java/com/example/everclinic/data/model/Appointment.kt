
/**
 * This file defines the Appointment data model.
 */
package com.example.everclinic.data.model

data class Appointment(
    val id: String,
    val patientId: String,
    val doctorId: String,
    val date: String,
    val time: String,
    val status: String,
    val reason: String,
    val notes: String
)
