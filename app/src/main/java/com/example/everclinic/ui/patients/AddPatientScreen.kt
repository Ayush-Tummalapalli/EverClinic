package com.example.everclinic.ui.patients

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.derivedStateOf
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
import com.example.everclinic.data.local.entities.PatientEntity
import com.example.everclinic.ui.ViewModelFactory
import com.google.gson.Gson
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddPatientScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val patientDao = app.database.patientDao()
    val doctorDao = app.database.doctorDao()
    val treatmentDao = app.database.treatmentDao()
    val appointmentDao = app.database.appointmentDao()
    val viewModel: AddPatientViewModel = viewModel(factory = ViewModelFactory(patientDao, doctorDao, appointmentDao, treatmentDao))

    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var emergencyContactName by remember { mutableStateOf("") }
    var emergencyContactPhone by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("A+") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var lastVisit by remember { mutableStateOf("") }
    var insuranceProvider by remember { mutableStateOf("") }
    var policyNumber by remember { mutableStateOf("") }
    var validity by remember { mutableStateOf("") }
    var smoking by remember { mutableStateOf(false) }
    var alcohol by remember { mutableStateOf(false) }
    var allergy by remember { mutableStateOf("") }
    val allergies = remember { mutableStateListOf<String>() }

    val bmi by remember {
        derivedStateOf {
            val h = height.toFloatOrNull()
            val w = weight.toFloatOrNull()
            if (h != null && w != null && h > 0) {
                val heightInMeters = h / 100
                (w / (heightInMeters * heightInMeters)).toString()
            } else {
                "Auto"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Patient") },
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
                BasicInformationSection(fullName, { fullName = it }, age, { age = it }, gender, { gender = it }, phoneNumber, { phoneNumber = it }, address, { address = it }, emergencyContactName, { emergencyContactName = it }, emergencyContactPhone, { emergencyContactPhone = it })
            }
            item {
                MedicalProfileSection(bloodGroup, { bloodGroup = it }, height, { height = it }, weight, { weight = it }, condition, { condition = it }, lastVisit, { lastVisit = it }, bmi, allergy, { allergy = it }, allergies, { allergies.add(it) })
            }
            item {
                InsuranceDetailsSection(insuranceProvider, { insuranceProvider = it }, policyNumber, { policyNumber = it }, validity, { validity = it })
            }
            item {
                LifestyleInformationSection(smoking, { smoking = it }, alcohol, { alcohol = it })
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
                            val patient = PatientEntity(
                                id = UUID.randomUUID().toString(),
                                name = fullName,
                                age = age.toIntOrNull() ?: 0,
                                gender = gender,
                                phone = phoneNumber,
                                address = address,
                                emergencyContactName = emergencyContactName,
                                emergencyContactPhone = emergencyContactPhone,
                                lastVisit = lastVisit,
                                condition = condition,
                                bloodGroup = bloodGroup,
                                height = height.toFloatOrNull(),
                                weight = weight.toFloatOrNull(),
                                bmi = bmi.toFloatOrNull(),
                                chronicConditions = "[]",
                                allergies = Gson().toJson(allergies),
                                currentMedications = "[]",
                                pastSurgeries = "[]",
                                smoking = smoking,
                                alcohol = alcohol,
                                activityLevel = "",
                                insuranceProvider = insuranceProvider,
                                policyNumber = policyNumber,
                                validity = validity
                            )
                            viewModel.addPatient(patient)
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add Patient")
                    }
                }
            }
        }
    }
}