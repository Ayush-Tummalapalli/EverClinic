package com.example.everclinic.ui.appointments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.example.everclinic.data.local.dao.AppointmentDao
import com.example.everclinic.data.local.dao.DoctorDao
import com.example.everclinic.data.local.dao.PatientDao
import com.example.everclinic.data.local.entities.AppointmentEntity
import com.example.everclinic.data.local.entities.DoctorEntity
import com.example.everclinic.data.local.entities.PatientEntity
import com.example.everclinic.data.local.relations.AppointmentDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class EditAppointmentViewModel(private val appointmentDao: AppointmentDao, private val appointmentId: String, patientDao: PatientDao, doctorDao: DoctorDao) : ViewModel() {
    val appointment: StateFlow<AppointmentDetails?> = appointmentDao.getAppointmentById(appointmentId)
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val patients: StateFlow<List<PatientEntity>> = patientDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val doctors: StateFlow<List<DoctorEntity>> = doctorDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            appointmentDao.insert(appointment)
        }
    }
}

class EditAppointmentViewModelFactory(private val appointmentDao: AppointmentDao, private val appointmentId: String, private val patientDao: PatientDao, private val doctorDao: DoctorDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditAppointmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditAppointmentViewModel(appointmentDao, appointmentId, patientDao, doctorDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAppointmentScreen(appointmentId: String, navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val patientDao = app.database.patientDao()
    val doctorDao = app.database.doctorDao()
    val appointmentDao = app.database.appointmentDao()
    val viewModel: EditAppointmentViewModel = viewModel(factory = EditAppointmentViewModelFactory(appointmentDao, appointmentId, patientDao, doctorDao))

    val appointment by viewModel.appointment.collectAsState()
    val patients by viewModel.patients.collectAsState()
    val doctors by viewModel.doctors.collectAsState()

    var selectedPatient by remember { mutableStateOf<PatientEntity?>(null) }
    var selectedDoctor by remember { mutableStateOf<DoctorEntity?>(null) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Scheduled") }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(appointment) {
        appointment?.let {
            selectedPatient = patients.find { p -> p.id == it.appointment.patientId }
            selectedDoctor = doctors.find { d -> d.id == it.appointment.doctorId }
            date = it.appointment.date
            time = it.appointment.time
            reason = it.appointment.reason
            status = it.appointment.status
            notes = it.appointment.notes ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Appointment") },
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
                Text("Appointment Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))

                PatientDropdown(patients, selectedPatient) { selectedPatient = it }
                Spacer(modifier = Modifier.height(16.dp))

                DoctorDropdown(doctors, selectedDoctor) { selectedDoctor = it }
                Spacer(modifier = Modifier.height(16.dp))

                DateTimePickers(date, { date = it }, time, { time = it })
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(value = reason, onValueChange = { reason = it }, label = { Text("Reason for Visit *") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                StatusDropdown(status) { status = it }
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Additional Notes") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
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
                            val updatedAppointment = appointment!!.appointment.copy(
                                patientId = selectedPatient?.id ?: "",
                                doctorId = selectedDoctor?.id ?: "",
                                date = date,
                                time = time,
                                reason = reason,
                                status = status,
                                notes = notes
                            )
                            viewModel.updateAppointment(updatedAppointment)
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Update Appointment") }
                }
            }
        }
    }
}
