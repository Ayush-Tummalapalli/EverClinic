
/**
 * This file defines the PatientEntity for the Room database.
 */
package com.example.everclinic.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey val id: String,
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
    val chronicConditions: String, // JSON string
    val allergies: String, // JSON string
    val currentMedications: String, // JSON string
    val pastSurgeries: String, // JSON string
    val smoking: Boolean,
    val alcohol: Boolean,
    val activityLevel: String,
    val insuranceProvider: String,
    val policyNumber: String,
    val validity: String
)
