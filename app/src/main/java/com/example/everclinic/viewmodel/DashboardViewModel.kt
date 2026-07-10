
/**
 * This file contains the ViewModel for the Dashboard screen.
 */
package com.example.everclinic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.repository.ClinicRepository
import com.example.everclinic.uiState.DashboardUiState
import com.example.everclinic.util.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: ClinicRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableStateFlow<UiEvent?>(null)
    val uiEvent: StateFlow<UiEvent?> = _uiEvent.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllPatients().onEach {
                _uiState.value = _uiState.value.copy(patientCount = it.size)
            }.launchIn(viewModelScope)

            repository.getAllDoctors().onEach {
                _uiState.value = _uiState.value.copy(doctorCount = it.size)
            }.launchIn(viewModelScope)

            repository.getAllAppointments().onEach {
                _uiState.value = _uiState.value.copy(appointmentCount = it.size)
            }.launchIn(viewModelScope)

            repository.getAllTreatments().onEach {
                _uiState.value = _uiState.value.copy(treatmentCount = it.size)
            }.launchIn(viewModelScope)
        }
    }
}
