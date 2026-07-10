package com.example.everclinic.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Patients : Screen("patients")
    object Doctors : Screen("doctors")
    object Appointments : Screen("appointments")
    object Treatments : Screen("treatments")
    object AddPatient : Screen("add_patient")
    object AddDoctor : Screen("add_doctor")
    object AddAppointment : Screen("add_appointment")
    object AddTreatment : Screen("add_treatment")
    object PatientDetail : Screen("patient_detail/{patientId}") {
        fun createRoute(patientId: String) = "patient_detail/$patientId"
    }
    object DoctorDetail : Screen("doctor_detail/{doctorId}") {
        fun createRoute(doctorId: String) = "doctor_detail/$doctorId"
    }
    object AppointmentDetail : Screen("appointment_detail/{appointmentId}") {
        fun createRoute(appointmentId: String) = "appointment_detail/$appointmentId"
    }
    object TreatmentDetail : Screen("treatment_detail/{treatmentId}") {
        fun createRoute(treatmentId: String) = "treatment_detail/$treatmentId"
    }
    object EditAppointment : Screen("edit_appointment/{appointmentId}") {
        fun createRoute(appointmentId: String) = "edit_appointment/$appointmentId"
    }
    object EditDoctor : Screen("edit_doctor/{doctorId}") {
        fun createRoute(doctorId: String) = "edit_doctor/$doctorId"
    }
    object EditPatient : Screen("edit_patient/{patientId}") {
        fun createRoute(patientId: String) = "edit_patient/$patientId"
    }
}