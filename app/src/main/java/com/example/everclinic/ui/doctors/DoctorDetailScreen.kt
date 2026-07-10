package com.example.everclinic.ui.doctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.everclinic.data.local.entities.DoctorEntity
import com.example.everclinic.navigation.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DoctorDetailViewModel(private val doctorDao: DoctorDao, doctorId: String) : ViewModel() {
    val doctor: StateFlow<DoctorEntity> = doctorDao.getById(doctorId)
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DoctorEntity("", "", "", 0, "", "", ""))

    fun deleteDoctor(doctor: DoctorEntity) {
        viewModelScope.launch {
            doctorDao.delete(doctor)
        }
    }
}

class DoctorDetailViewModelFactory(private val doctorDao: DoctorDao, private val doctorId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DoctorDetailViewModel(doctorDao, doctorId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun DoctorDetailScreen(doctorId: String, navController: NavController) {
    val context = LocalContext.current
    val doctorDao = (context.applicationContext as EverclinicApplication).database.doctorDao()
    val viewModel: DoctorDetailViewModel = viewModel(factory = DoctorDetailViewModelFactory(doctorDao, doctorId))
    val doctor by viewModel.doctor.collectAsState()

    Scaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(it)
        ) {
            item {
                DoctorDetailHeader(doctor, navController)
            }
            item {
                ContactInformationCard(doctor)
            }
            item {
                AvailabilityCard(doctor)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.deleteDoctor(doctor)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    Text("Delete Doctor")
                }
            }
        }
    }
}

@Composable
fun DoctorDetailHeader(doctor: DoctorEntity, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF4CAF50),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .padding(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Screen.EditDoctor.createRoute(doctor.id)) }) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
            }
        }
        Text(doctor.name, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(doctor.specialty, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.3f))
        ) {
            Text(
                text = "${doctor.experience} years of experience",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                color = Color.White
            )
        }
    }
}

@Composable
fun ContactInformationCard(doctor: DoctorEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Contact Information", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            InfoRow(icon = Icons.Filled.Call, label = "Phone", value = doctor.phone)
            InfoRow(icon = Icons.Filled.Email, label = "Email", value = doctor.email)
        }
    }
}

@Composable
fun AvailabilityCard(doctor: DoctorEntity) {
    val availability: List<String> = remember(doctor.availability) {
        val type = object : TypeToken<List<String>>() {}.type
        try {
            if (doctor.availability.isNotBlank()) {
                Gson().fromJson(doctor.availability, type)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList<String>()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Availability", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            availability.forEach { day ->
                Text(day, modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = label, tint = Color.Gray)
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(label, color = Color.Gray, fontSize = 12.sp)
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}