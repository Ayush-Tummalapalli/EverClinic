
/**
 * This file contains the DoctorProfileScreen composable, which displays the details
 * of a single doctor.
 * Mapped from DoctorProfile.tsx.
 */
package com.example.everclinic.ui.screens.Doctors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun DoctorProfileScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    doctorId: String
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "Doctor Profile Screen for doctor $doctorId")
    }
}
