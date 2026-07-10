
/**
 * This file contains the ViewModel for the Doctors screens.
 */
package com.example.everclinic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.model.Doctor
import com.example.everclinic.data.repository.ClinicRepository
import com.example.everclinic.uiState.DoctorsUiState
import com.example.everclinic.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DoctorsViewModel(private val repository: ClinicRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorsUiState())
    val uiState: StateFlow<DoctorsUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadDoctors()
    }

    private fun loadDoctors() {
        repository.getAllDoctors().onEach {
            _uiState.value = _uiState.value.copy(doctors = it)
        }.launchIn(viewModelScope)
    }

    fun onAddDoctor(doctor: Doctor) = viewModelScope.launch {
        repository.insertDoctor(doctor).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Doctor added successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error adding doctor"))
        }
    }

    fun onUpdateDoctor(doctor: Doctor) = viewModelScope.launch {
        repository.updateDoctor(doctor).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Doctor updated successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error updating doctor"))
        }
    }

    fun onDeleteDoctor(id: String) = viewModelScope.launch {
        repository.deleteDoctor(id).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Doctor deleted successfully"))
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error deleting doctor"))
        }
    }

    fun onSearchDoctors(query: String) {
        repository.searchDoctors(query).onEach {
            _uiState.value = _uiState.value.copy(doctors = it)
        }.launchIn(viewModelScope)
    }
}
