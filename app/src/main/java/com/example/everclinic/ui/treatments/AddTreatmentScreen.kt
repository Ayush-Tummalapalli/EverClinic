package com.example.everclinic.ui.treatments

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.everclinic.EverclinicApplication
import com.example.everclinic.data.local.dao.DoctorDao
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.dao.TreatmentDao
import com.example.everclinic.data.local.entities.DoctorEntity
import com.example.everclinic.data.local.entities.PatientEntity
import com.example.everclinic.data.local.entities.TreatmentEntity
import com.example.everclinic.ui.ViewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

class AddTreatmentViewModel(patientDao: PatientDao, doctorDao: DoctorDao, private val treatmentDao: TreatmentDao) : ViewModel() {
    val patients: StateFlow<List<PatientEntity>> = patientDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val doctors: StateFlow<List<DoctorEntity>> = doctorDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTreatment(treatment: TreatmentEntity) {
        viewModelScope.launch {
            treatmentDao.insert(treatment)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTreatmentScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val patientDao = app.database.patientDao()
    val doctorDao = app.database.doctorDao()
    val treatmentDao = app.database.treatmentDao()
    val appointmentDao = app.database.appointmentDao()
    val viewModel: AddTreatmentViewModel = viewModel(factory = ViewModelFactory(patientDao, doctorDao, appointmentDao, treatmentDao))

    val patients by viewModel.patients.collectAsState()
    val doctors by viewModel.doctors.collectAsState()

    var selectedPatient by remember { mutableStateOf<PatientEntity?>(null) }
    var selectedDoctor by remember { mutableStateOf<DoctorEntity?>(null) }
    var treatmentDate by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var prescription by remember { mutableStateOf("") }
    var followUpDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Active") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Treatment Record") },
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
            item { Text("Basic Information", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { PatientDropdown(patients, selectedPatient) { selectedPatient = it } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DoctorDropdown(doctors, selectedDoctor) { selectedDoctor = it } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DatePickerField("Treatment Date *", treatmentDate) { treatmentDate = it } }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Text("Medical Details", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { OutlinedTextField(value = diagnosis, onValueChange = { diagnosis = it }, label = { Text("Diagnosis *") }, modifier = Modifier.fillMaxWidth(), minLines = 3) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Treatment Description *") }, modifier = Modifier.fillMaxWidth(), minLines = 3) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { OutlinedTextField(value = prescription, onValueChange = { prescription = it }, label = { Text("Prescription *") }, modifier = Modifier.fillMaxWidth(), minLines = 3) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DatePickerField("Follow-up Date (Optional)", followUpDate) { followUpDate = it } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { StatusDropdown(status) { status = it } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Additional Notes") }, modifier = Modifier.fillMaxWidth(), minLines = 3) }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Row {
                    Button(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) { Text("Cancel") }
                    Spacer(modifier = Modifier.weight(0.1f))
                    Button(
                        onClick = {
                            val treatment = TreatmentEntity(
                                id = UUID.randomUUID().toString(),
                                patientId = selectedPatient?.id ?: "",
                                doctorId = selectedDoctor?.id ?: "",
                                date = treatmentDate,
                                diagnosis = diagnosis,
                                description = description,
                                prescription = prescription,
                                followUpDate = followUpDate,
                                notes = notes,
                                status = status
                            )
                            viewModel.addTreatment(treatment)
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Add Record") }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDropdown(patients: List<PatientEntity>, selectedPatient: PatientEntity?, onPatientSelected: (PatientEntity) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(value = selectedPatient?.name ?: "", onValueChange = {}, label = { Text("Select Patient *") }, readOnly = true, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            patients.forEach { patient ->
                DropdownMenuItem(text = { Text(patient.name) }, onClick = { onPatientSelected(patient); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDropdown(doctors: List<DoctorEntity>, selectedDoctor: DoctorEntity?, onDoctorSelected: (DoctorEntity) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(value = selectedDoctor?.name ?: "", onValueChange = {}, label = { Text("Select Doctor *") }, readOnly = true, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            doctors.forEach { doctor ->
                DropdownMenuItem(text = { Text(doctor.name) }, onClick = { onDoctorSelected(doctor); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusDropdown(status: String, onStatusSelected: (String) -> Unit) {
    val statuses = listOf("Active", "Completed", "Cancelled")
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(value = status, onValueChange = {}, label = { Text("Status *") }, readOnly = true, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            statuses.forEach { status ->
                DropdownMenuItem(text = { Text(status) }, onClick = { onStatusSelected(status); expanded = false })
            }
        }
    }
}

@Composable
fun DatePickerField(label: String, date: String, onDateChange: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int -> onDateChange("$dayOfMonth/${month + 1}/$year") }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

    OutlinedTextField(value = date, onValueChange = {}, label = { Text(label) }, readOnly = true, interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect {
                if (it is PressInteraction.Release) {
                    datePickerDialog.show()
                }
            }
        }
    }, modifier = Modifier.fillMaxWidth())
}
