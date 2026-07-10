
/**
 * This file contains the ViewModel for the Patients screens.
 */
package com.example.everclinic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.model.Patient
import com.example.everclinic.data.repository.ClinicRepository
import com.example.everclinic.uiState.PatientsUiState
import com.example.everclinic.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PatientsViewModel(private val repository: ClinicRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientsUiState())
    val uiState: StateFlow<PatientsUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadPatients()
    }

    private fun loadPatients() {
        repository.getAllPatients().onEach {
            _uiState.value = _uiState.value.copy(patients = it)
        }.launchIn(viewModelScope)
    }

    fun onAddPatient(patient: Patient) = viewModelScope.launch {
        repository.insertPatient(patient).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Patient added successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error adding patient"))
        }
    }

    fun onUpdatePatient(patient: Patient) = viewModelScope.launch {
        repository.updatePatient(patient).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Patient updated successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error updating patient"))
        }
    }

    fun onDeletePatient(id: String) = viewModelScope.launch {
        repository.deletePatient(id).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Patient deleted successfully"))
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error deleting patient"))
        }
    }

    fun onSearchPatients(query: String) {
        repository.searchPatients(query).onEach {
            _uiState.value = _uiState.value.copy(patients = it)
        }.launchIn(viewModelScope)
    }
}
