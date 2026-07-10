
/**
 * This file defines the UI state for the Patients screens.
 */
package com.example.everclinic.uiState

import com.example.everclinic.data.model.Patient

data class PatientsUiState(
    val isLoading: Boolean = false,
    val patients: List<Patient> = emptyList(),
    val error: String? = null
)
