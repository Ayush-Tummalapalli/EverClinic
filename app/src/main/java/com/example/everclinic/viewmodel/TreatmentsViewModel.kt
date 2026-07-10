
/**
 * This file contains the ViewModel for the Treatments screens.
 */
package com.example.everclinic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.model.Treatment
import com.example.everclinic.data.repository.ClinicRepository
import com.example.everclinic.uiState.TreatmentsUiState
import com.example.everclinic.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TreatmentsViewModel(private val repository: ClinicRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TreatmentsUiState())
    val uiState: StateFlow<TreatmentsUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadTreatments()
    }

    private fun loadTreatments() {
        repository.getAllTreatments().onEach {
            _uiState.value = _uiState.value.copy(treatments = it)
        }.launchIn(viewModelScope)
    }

    fun onAddTreatment(treatment: Treatment) = viewModelScope.launch {
        repository.insertTreatment(treatment).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Treatment added successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error adding treatment"))
        }
    }

    fun onUpdateTreatment(treatment: Treatment) = viewModelScope.launch {
        repository.updateTreatment(treatment).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Treatment updated successfully"))
            _uiEvent.emit(UiEvent.NavigateBack)
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error updating treatment"))
        }
    }

    fun onDeleteTreatment(id: String) = viewModelScope.launch {
        repository.deleteTreatment(id).onSuccess {
            _uiEvent.emit(UiEvent.ShowSnackbar("Treatment deleted successfully"))
        }.onFailure {
            _uiEvent.emit(UiEvent.ShowSnackbar("Error deleting treatment"))
        }
    }
}
