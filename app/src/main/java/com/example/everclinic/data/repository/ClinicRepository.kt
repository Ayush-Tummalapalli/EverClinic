
/**
 * This file defines the interface for the clinic repository.
 */
package com.example.everclinic.data.repository

import com.example.everclinic.data.model.Appointment
import com.example.everclinic.data.model.Doctor
import com.example.everclinic.data.model.Patient
import com.example.everclinic.data.model.Treatment
import kotlinx.coroutines.flow.Flow

interface ClinicRepository {
    // Patients
    fun getAllPatients(): Flow<List<Patient>>
    fun getPatientById(id: String): Flow<Patient?>
    suspend fun insertPatient(patient: Patient): Result<Unit>
    suspend fun updatePatient(patient: Patient): Result<Unit>
    suspend fun deletePatient(id: String): Result<Unit>
    fun searchPatients(query: String): Flow<List<Patient>>

    // Doctors
    fun getAllDoctors(): Flow<List<Doctor>>
    fun getDoctorById(id: String): Flow<Doctor?>
    suspend fun insertDoctor(doctor: Doctor): Result<Unit>
    suspend fun updateDoctor(doctor: Doctor): Result<Unit>
    suspend fun deleteDoctor(id: String): Result<Unit>
    fun searchDoctors(query: String): Flow<List<Doctor>>

    // Appointments
    fun getAllAppointments(): Flow<List<Appointment>>
    fun getAppointmentById(id: String): Flow<Appointment?>
    suspend fun insertAppointment(appointment: Appointment): Result<Unit>
    suspend fun updateAppointment(appointment: Appointment): Result<Unit>
    suspend fun deleteAppointment(id: String): Result<Unit>

    // Treatments
    fun getAllTreatments(): Flow<List<Treatment>>
    fun getTreatmentById(id: String): Flow<Treatment?>
    suspend fun insertTreatment(treatment: Treatment): Result<Unit>
    suspend fun updateTreatment(treatment: Treatment): Result<Unit>
    suspend fun deleteTreatment(id: String): Result<Unit>
}
