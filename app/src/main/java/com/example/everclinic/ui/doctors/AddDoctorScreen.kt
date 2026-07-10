package com.example.everclinic.ui.doctors

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.everclinic.EverclinicApplication
import com.example.everclinic.data.local.entities.DoctorEntity
import com.example.everclinic.ui.ViewModelFactory
import com.google.gson.Gson
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDoctorScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val doctorDao = app.database.doctorDao()
    val patientDao = app.database.patientDao()
    val appointmentDao = app.database.appointmentDao()
    val treatmentDao = app.database.treatmentDao()
    val viewModel: AddDoctorViewModel = viewModel(factory = ViewModelFactory(patientDao, doctorDao, appointmentDao, treatmentDao))

    var fullName by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val availability = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Doctor") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                BasicInformationSection(fullName, { fullName = it }, specialty, { specialty = it }, experience, { experience = it }, phoneNumber, { phoneNumber = it }, email, { email = it })
            }
            item {
                AvailabilitySection(availability, { day ->
                    if (availability.contains(day)) {
                        availability.remove(day)
                    } else {
                        availability.add(day)
                    }
                })
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                    Button(
                        onClick = {
                            val doctor = DoctorEntity(
                                id = UUID.randomUUID().toString(),
                                name = fullName,
                                specialty = specialty,
                                experience = experience.toIntOrNull() ?: 0,
                                phone = phoneNumber,
                                email = email,
                                availability = Gson().toJson(availability)
                            )
                            viewModel.addDoctor(doctor)
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add Doctor")
                    }
                }
            }
        }
    }
}
