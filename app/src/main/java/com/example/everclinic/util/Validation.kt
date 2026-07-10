
/**
 * This file contains utility functions for validation.
 */
package com.example.everclinic.util

import android.util.Patterns

object Validation {

    fun validatePatient(name: String, age: String, phone: String): List<String> {
        val errors = mutableListOf<String>()
        if (name.isBlank()) {
            errors.add("Name cannot be empty")
        }
        if (age.isBlank() || age.toIntOrNull() == null) {
            errors.add("Invalid age")
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            errors.add("Invalid phone number")
        }
        return errors
    }

    fun validateDoctor(name: String, specialty: String, experience: String, phone: String, email: String): List<String> {
        val errors = mutableListOf<String>()
        if (name.isBlank()) {
            errors.add("Name cannot be empty")
        }
        if (specialty.isBlank()) {
            errors.add("Specialty cannot be empty")
        }
        if (experience.isBlank() || experience.toIntOrNull() == null) {
            errors.add("Invalid experience")
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            errors.add("Invalid phone number")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.add("Invalid email address")
        }
        return errors
    }

    fun validateAppointment(patientId: String, doctorId: String, date: String, time: String, reason: String): List<String> {
        val errors = mutableListOf<String>()
        if (patientId.isBlank()) {
            errors.add("Patient cannot be empty")
        }
        if (doctorId.isBlank()) {
            errors.add("Doctor cannot be empty")
        }
        if (date.isBlank()) {
            errors.add("Date cannot be empty")
        }
        if (time.isBlank()) {
            errors.add("Time cannot be empty")
        }
        if (reason.isBlank()) {
            errors.add("Reason cannot be empty")
        }
        return errors
    }

    fun validateTreatment(patientId: String, doctorId: String, date: String, diagnosis: String, treatment: String, prescription: String): List<String> {
        val errors = mutableListOf<String>()
        if (patientId.isBlank()) {
            errors.add("Patient cannot be empty")
        }
        if (doctorId.isBlank()) {
            errors.add("Doctor cannot be empty")
        }
        if (date.isBlank()) {
            errors.add("Date cannot be empty")
        }
        if (diagnosis.isBlank()) {
            errors.add("Diagnosis cannot be empty")
        }
        if (treatment.isBlank()) {
            errors.add("Treatment cannot be empty")
        }
        if (prescription.isBlank()) {
            errors.add("Prescription cannot be empty")
        }
        return errors
    }
}
