
/**
 * This file contains the ViewModel for the Appointments screens.
 */
package com.example.everclinic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.model.Appointment
import com.example.everclinic.data.repository.ClinicRepository
import com.example.everclinic.uiState.AppointmentsUiState
import com.example.everclinic.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AppointmentsViewModel(private val repository: ClinicRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentsUiState())
    val uiState: StateFlow<AppointmentsUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadAppointments()
    }

    private fun loadAppointments() {
        repository.getAllAppointments().onEach {
            _uiState.value = _uiState.value.copy(appointments = it)
        }.launchIn(viewModelScope)
    }

    fun onAddAppointment(appointment: Appointment) = viewModelScope.launch {
        repository.insertAppointment(appointment).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Appointment added successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error adding appointment"))
        }
    }

    fun onUpdateAppointment(appointment: Appointment) = viewModelScope.launch {
        repository.updateAppointment(appointment).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Appointment updated successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error updating appointment"))
        }
    }

    fun onDeleteAppointment(id: String) = viewModelScope.launch {
        repository.deleteAppointment(id).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Appointment deleted successfully"))
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error deleting appointment"))
        }
    }
}
