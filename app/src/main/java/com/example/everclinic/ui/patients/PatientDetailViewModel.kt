package com.example.everclinic.ui.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.entities.PatientEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PatientDetailViewModel(private val patientDao: PatientDao, patientId: String) : ViewModel() {

    val patient: StateFlow<PatientEntity?> = patientDao.getById(patientId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun deletePatient() {
        viewModelScope.launch {
            patient.value?.let { patientDao.delete(it) }
        }
    }
}

class PatientDetailViewModelFactory(private val patientDao: PatientDao, private val patientId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientDetailViewModel(patientDao, patientId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}