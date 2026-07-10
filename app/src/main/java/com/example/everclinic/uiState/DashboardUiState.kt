
/**
 * This file defines the UI state for the Dashboard screen.
 */
package com.example.everclinic.uiState

data class DashboardUiState(
    val isLoading: Boolean = false,
    val patientCount: Int = 0,
    val doctorCount: Int = 0,
    val appointmentCount: Int = 0,
    val treatmentCount: Int = 0,
    val error: String? = null
)
