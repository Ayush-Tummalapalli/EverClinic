
/**
 * This file contains the AppointmentDetailsScreen composable, which displays the details
 * of a single appointment.
 * Mapped from AppointmentDetails.tsx.
 */
package com.example.everclinic.ui.screens.Appointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AppointmentDetailsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    appointmentId: String
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "Appointment Details Screen for appointment $appointmentId")
    }
}
