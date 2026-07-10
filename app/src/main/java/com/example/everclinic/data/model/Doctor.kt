
/**
 * This file defines the Doctor data model.
 */
package com.example.everclinic.data.model

data class Doctor(
    val id: String,
    val name: String,
    val specialty: String,
    val experience: Int,
    val phone: String,
    val email: String,
    val availability: List<String>,
    val photoUrl: String?
)
