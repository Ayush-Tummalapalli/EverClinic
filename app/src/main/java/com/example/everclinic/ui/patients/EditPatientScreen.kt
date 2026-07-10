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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.everclinic.EverclinicApplication
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.entities.PatientEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditPatientViewModel(private val patientDao: PatientDao, private val patientId: String) : ViewModel() {
    val patient: StateFlow<PatientEntity?> = patientDao.getById(patientId)
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updatePatient(patient: PatientEntity) {
        viewModelScope.launch {
            patientDao.update(patient)
        }
    }
}

class EditPatientViewModelFactory(private val patientDao: PatientDao, private val patientId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditPatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditPatientViewModel(patientDao, patientId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditPatientScreen(patientId: String, navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val patientDao = app.database.patientDao()
    val viewModel: EditPatientViewModel = viewModel(factory = EditPatientViewModelFactory(patientDao, patientId))

    val patient by viewModel.patient.collectAsState()

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

    LaunchedEffect(patient) {
        patient?.let {
            fullName = it.name
            age = it.age.toString()
            gender = it.gender
            phoneNumber = it.phone
            address = it.address
            emergencyContactName = it.emergencyContactName
            emergencyContactPhone = it.emergencyContactPhone
            lastVisit = it.lastVisit
            condition = it.condition
            bloodGroup = it.bloodGroup
            height = it.height?.toString() ?: ""
            weight = it.weight?.toString() ?: ""
            val type = object : TypeToken<List<String>>() {}.type
            val allergyList: List<String> = try {
                if (it.allergies.isNotBlank()) {
                    Gson().fromJson(it.allergies, type)
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList<String>()
            }
            allergies.clear()
            allergies.addAll(allergyList)
            smoking = it.smoking
            alcohol = it.alcohol
            insuranceProvider = it.insuranceProvider
            policyNumber = it.policyNumber
            validity = it.validity
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Patient") },
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
                            val updatedPatient = patient!!.copy(
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
                                allergies = Gson().toJson(allergies),
                                smoking = smoking,
                                alcohol = alcohol,
                                insuranceProvider = insuranceProvider,
                                policyNumber = policyNumber,
                                validity = validity
                            )
                            viewModel.updatePatient(updatedPatient)
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Update Patient") }
                }
            }
        }
    }
}