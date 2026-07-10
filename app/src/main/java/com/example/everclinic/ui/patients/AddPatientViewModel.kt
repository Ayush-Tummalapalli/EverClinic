package com.example.everclinic.ui.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.entities.PatientEntity
import kotlinx.coroutines.launch

class AddPatientViewModel(private val patientDao: PatientDao) : ViewModel() {

    fun addPatient(patient: PatientEntity) {
        viewModelScope.launch {
            patientDao.insert(patient)
        }
    }
}