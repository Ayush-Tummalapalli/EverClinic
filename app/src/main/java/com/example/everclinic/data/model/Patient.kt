
/**
 * This file defines the Patient data model.
 */
package com.example.everclinic.data.model

data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val phone: String,
    val address: String,
    val emergencyContactName: String,
    val emergencyContactPhone: String,
    val lastVisit: String,
    val condition: String,
    val bloodGroup: String,
    val height: Float?,
    val weight: Float?,
    val bmi: Float?,
    val chronicConditions: List<String>,
    val allergies: List<String>,
    val currentMedications: List<Medication>,
    val pastSurgeries: List<Surgery>,
    val smoking: Boolean,
    val alcohol: Boolean,
    val activityLevel: String,
    val insuranceProvider: String,
    val policyNumber: String,
    val validity: String
)

data class Medication(val name: String, val dosage: String)

data class Surgery(val name: String, val date: String)
