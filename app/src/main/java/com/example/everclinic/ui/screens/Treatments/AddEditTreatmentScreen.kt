
/**
 * This file contains the AddEditTreatmentScreen composable, which is a form for
 * adding or editing a treatment.
 * Mapped from AddEditTreatment.tsx.
 */
package com.example.everclinic.ui.screens.Treatments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddEditTreatmentScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    treatmentId: String? = null
) {
    var patientId by remember { mutableStateOf("") }
    var doctorId by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    var treatment by remember { mutableStateOf("") }
    var prescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = patientId,
            onValueChange = { patientId = it },
            label = { Text("Patient ID") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = doctorId,
            onValueChange = { doctorId = it },
            label = { Text("Doctor ID") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = diagnosis,
            onValueChange = { diagnosis = it },
            label = { Text("Diagnosis") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = treatment,
            onValueChange = { treatment = it },
            label = { Text("Treatment") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = prescription,
            onValueChange = { prescription = it },
            label = { Text("Prescription") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { navController.popBackStack() }) {
            Text("Save")
        }
    }
}
