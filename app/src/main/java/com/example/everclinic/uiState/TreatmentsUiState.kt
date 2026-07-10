
/**
 * This file defines the UI state for the Treatments screens.
 */
package com.example.everclinic.uiState

import com.example.everclinic.data.model.Treatment

data class TreatmentsUiState(
    val isLoading: Boolean = false,
    val treatments: List<Treatment> = emptyList(),
    val error: String? = null
)
