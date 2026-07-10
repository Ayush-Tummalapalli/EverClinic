
/**
 * This file defines the UI state for the Appointments screens.
 */
package com.example.everclinic.uiState

import com.example.everclinic.data.model.Appointment

data class AppointmentsUiState(
    val isLoading: Boolean = false,
    val appointments: List<Appointment> = emptyList(),
    val error: String? = null
)
