package com.example.everclinic.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everclinic.data.local.dao.AppointmentDao
import com.example.everclinic.data.local.dao.DoctorDao
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.dao.TreatmentDao
import com.example.everclinic.data.local.relations.AppointmentDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HomeUiState(
    val totalPatients: Int = 0,
    val totalDoctors: Int = 0,
    val totalAppointments: Int = 0,
    val totalTreatments: Int = 0,
    val todayAppointments: List<AppointmentDetails> = emptyList()
)

class HomeViewModel(
    patientDao: PatientDao,
    doctorDao: DoctorDao,
    appointmentDao: AppointmentDao,
    treatmentDao: TreatmentDao
) : ViewModel() {

    private val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    val uiState: StateFlow<HomeUiState> = combine(
        patientDao.getAll(),
        doctorDao.getAll(),
        appointmentDao.getAppointmentsCount(),
        treatmentDao.getTreatmentsCount(),
        appointmentDao.getAppointmentsByDate(today)
    ) { patients, doctors, appointmentsCount, treatmentsCount, todayAppointments ->
        HomeUiState(
            totalPatients = patients.size,
            totalDoctors = doctors.size,
            totalAppointments = appointmentsCount,
            totalTreatments = treatmentsCount,
            todayAppointments = todayAppointments
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )
}
