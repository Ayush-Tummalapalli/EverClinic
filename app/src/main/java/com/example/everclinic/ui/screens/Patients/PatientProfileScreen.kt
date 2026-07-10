
/**
 * This file contains the PatientProfileScreen composable, which displays the details
 * of a single patient.
 * Mapped from PatientProfile.tsx.
 */
package com.example.everclinic.ui.screens.Patients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun PatientProfileScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    patientId: String
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "Patient Profile Screen for patient $patientId")
    }
}
