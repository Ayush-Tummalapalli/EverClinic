package com.example.everclinic.ui.appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.local.dao.AppointmentDao
import com.example.everclinic.data.local.dao.DoctorDao
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.entities.AppointmentEntity
import com.example.everclinic.data.local.entities.DoctorEntity
import com.example.everclinic.data.local.entities.PatientEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddAppointmentViewModel(patientDao: PatientDao, doctorDao: DoctorDao, private val appointmentDao: AppointmentDao) : ViewModel() {
    val patients: StateFlow<List<PatientEntity>> = patientDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val doctors: StateFlow<List<DoctorEntity>> = doctorDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            appointmentDao.insert(appointment)
        }
    }
}
