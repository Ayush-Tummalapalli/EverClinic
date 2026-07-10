
/**
 * This file defines the UI state for the Doctors screens.
 */
package com.example.everclinic.uiState

import com.example.everclinic.data.model.Doctor

data class DoctorsUiState(
    val isLoading: Boolean = false,
    val doctors: List<Doctor> = emptyList(),
    val error: String? = null
)
