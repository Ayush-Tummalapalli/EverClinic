
/**
 * This file contains all the navigation routes used in the application.
 */
package com.example.everclinic.navigation

object Routes {
    const val DASHBOARD = "dashboard"
    const val SEARCH = "search"

    const val PATIENTS = "patients"
    const val ADD_PATIENT = "add_patient"
    const val EDIT_PATIENT = "edit_patient/{id}"
    const val PATIENT_PROFILE = "patient_profile/{id}"

    const val DOCTORS = "doctors"
    const val ADD_DOCTOR = "add_doctor"
    const val EDIT_DOCTOR = "edit_doctor/{id}"
    const val DOCTOR_PROFILE = "doctor_profile/{id}"

    const val APPOINTMENTS = "appointments"
    const val ADD_APPOINTMENT = "add_appointment"
    const val EDIT_APPOINTMENT = "edit_appointment/{id}"
    const val APPOINTMENT_DETAILS = "appointment_details/{id}"

    const val TREATMENTS = "treatments"
    const val ADD_TREATMENT = "add_treatment"
    const val EDIT_TREATMENT = "edit_treatment/{id}"
}
