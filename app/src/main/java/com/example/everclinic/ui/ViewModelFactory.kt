package com.example.everclinic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.everclinic.data.local.dao.AppointmentDao
import com.example.everclinic.data.local.dao.DoctorDao
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.dao.TreatmentDao
import com.example.everclinic.ui.appointments.AddAppointmentViewModel
import com.example.everclinic.ui.appointments.AppointmentsViewModel
import com.example.everclinic.ui.doctors.AddDoctorViewModel
import com.example.everclinic.ui.doctors.DoctorsViewModel
import com.example.everclinic.ui.home.HomeViewModel
import com.example.everclinic.ui.patients.AddPatientViewModel
import com.example.everclinic.ui.patients.PatientsViewModel
import com.example.everclinic.ui.treatments.AddTreatmentViewModel
import com.example.everclinic.ui.treatments.TreatmentsViewModel

class ViewModelFactory(
    private val patientDao: PatientDao,
    private val doctorDao: DoctorDao,
    private val appointmentDao: AppointmentDao,
    private val treatmentDao: TreatmentDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(patientDao, doctorDao, appointmentDao, treatmentDao) as T
        }
        if (modelClass.isAssignableFrom(AddPatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPatientViewModel(patientDao) as T
        }
        if (modelClass.isAssignableFrom(PatientsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientsViewModel(patientDao) as T
        }
        if (modelClass.isAssignableFrom(AddDoctorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddDoctorViewModel(doctorDao) as T
        }
        if (modelClass.isAssignableFrom(DoctorsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DoctorsViewModel(doctorDao) as T
        }
        if (modelClass.isAssignableFrom(AddAppointmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddAppointmentViewModel(patientDao, doctorDao, appointmentDao) as T
        }
        if (modelClass.isAssignableFrom(AppointmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppointmentsViewModel(appointmentDao) as T
        }
        if (modelClass.isAssignableFrom(AddTreatmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTreatmentViewModel(patientDao, doctorDao, treatmentDao) as T
        }
        if (modelClass.isAssignableFrom(TreatmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TreatmentsViewModel(treatmentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}