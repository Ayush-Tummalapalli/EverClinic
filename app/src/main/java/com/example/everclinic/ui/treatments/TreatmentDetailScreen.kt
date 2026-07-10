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
import androidx.lifecycle.ViewModelProvider
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
import com.example.everclinic.data.local.relations.TreatmentDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class TreatmentDetailViewModel(private val treatmentDao: TreatmentDao, private val treatmentId: String, patientDao: PatientDao, doctorDao: DoctorDao) : ViewModel() {
    val treatment: StateFlow<TreatmentDetails?> = treatmentDao.getTreatmentById(treatmentId)
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val patients: StateFlow<List<PatientEntity>> = patientDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val doctors: StateFlow<List<DoctorEntity>> = doctorDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateTreatment(treatment: TreatmentEntity) {
        viewModelScope.launch {
            treatmentDao.insert(treatment)
        }
    }

    fun deleteTreatment(treatment: TreatmentEntity) {
        viewModelScope.launch {
            treatmentDao.delete(treatment)
        }
    }
}

class TreatmentDetailViewModelFactory(private val treatmentDao: TreatmentDao, private val treatmentId: String, private val patientDao: PatientDao, private val doctorDao: DoctorDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreatmentDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TreatmentDetailViewModel(treatmentDao, treatmentId, patientDao, doctorDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentDetailScreen(treatmentId: String, navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val patientDao = app.database.patientDao()
    val doctorDao = app.database.doctorDao()
    val treatmentDao = app.database.treatmentDao()
    val viewModel: TreatmentDetailViewModel = viewModel(factory = TreatmentDetailViewModelFactory(treatmentDao, treatmentId, patientDao, doctorDao))

    val treatment by viewModel.treatment.collectAsState()
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

    LaunchedEffect(treatment) {
        treatment?.let {
            selectedPatient = patients.find { p -> p.id == it.treatment.patientId }
            selectedDoctor = doctors.find { d -> d.id == it.treatment.doctorId }
            treatmentDate = it.treatment.date
            diagnosis = it.treatment.diagnosis
            description = it.treatment.description
            prescription = it.treatment.prescription
            followUpDate = it.treatment.followUpDate ?: ""
            notes = it.treatment.notes ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Treatment Record") },
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
            item { OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Additional Notes") }, modifier = Modifier.fillMaxWidth(), minLines = 3) }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Row {
                    Button(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) { Text("Cancel") }
                    Spacer(modifier = Modifier.weight(0.1f))
                    Button(
                        onClick = {
                            val updatedTreatment = treatment!!.treatment.copy(
                                patientId = selectedPatient?.id ?: "",
                                doctorId = selectedDoctor?.id ?: "",
                                date = treatmentDate,
                                diagnosis = diagnosis,
                                description = description,
                                prescription = prescription,
                                followUpDate = followUpDate,
                                notes = notes
                            )
                            viewModel.updateTreatment(updatedTreatment)
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Update Record") }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.deleteTreatment(treatment!!.treatment)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete Record")
                }
            }
        }
    }
}
