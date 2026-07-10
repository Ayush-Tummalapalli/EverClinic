package com.example.everclinic.ui.patients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.everclinic.EverclinicApplication
import com.example.everclinic.data.local.entities.PatientEntity
import com.example.everclinic.navigation.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun PatientDetailScreen(patientId: String, navController: NavController) {
    val context = LocalContext.current
    val patientDao = (context.applicationContext as EverclinicApplication).database.patientDao()
    val viewModel: PatientDetailViewModel = viewModel(factory = PatientDetailViewModelFactory(patientDao, patientId))
    val patient by viewModel.patient.collectAsState()

    Scaffold(bottomBar = {
        Button(
            onClick = {
                viewModel.deletePatient()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete Patient")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Delete Patient")
        }
    }) {
        patient?.let { pat ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(it)
            ) {
                item {
                    PatientDetailHeader(pat, navController)
                }
                item {
                    BasicInfoCard(pat)
                }
                item {
                    MedicalInfoCard(pat)
                }
                item {
                    AllergiesCard(pat.allergies)
                }
                item {
                    InsuranceCard(pat)
                }
                item {
                    LifestyleCard(pat)
                }
            }
        }
    }
}

@Composable
fun PatientDetailHeader(patient: PatientEntity, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF6A1B9A),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate(Screen.EditPatient.createRoute(patient.id)) }) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
            }
        }
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFE1BEE7)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = patient.name.take(2).uppercase(),
                color = Color(0xFF6A1B9A),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(patient.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text("${patient.age} years • ${patient.gender} • ${patient.bloodGroup}", color = Color.White.copy(alpha = 0.8f))
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2))) {
                Icon(Icons.Filled.Call, contentDescription = "Call")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Call")
            }
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                Icon(Icons.Filled.Message, contentDescription = "Message")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Message")
            }
        }
    }
}

@Composable
fun BasicInfoCard(patient: PatientEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Basic Information", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            InfoRow("Phone", patient.phone)
            InfoRow("Address", patient.address)
            InfoRow("Emergency Contact", "${patient.emergencyContactName} • ${patient.emergencyContactPhone}")
            InfoRow("Last Visit", patient.lastVisit)
        }
    }
}


@Composable
fun MedicalInfoCard(patient: PatientEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Medical Information", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                VitalSign("Height", patient.height?.let { "$it cm" } ?: "N/A")
                VitalSign("Weight", patient.weight?.let { "$it kg" } ?: "N/A")
                VitalSign("BMI", "${patient.bmi?.let { String.format("%.1f", it) } ?: "N/A"}")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllergiesCard(allergiesJson: String) {
    val allergies: List<String> = remember(allergiesJson) {
        val type = object : TypeToken<List<String>>() {}.type
        try {
            if (allergiesJson.isNotBlank()) {
                Gson().fromJson(allergiesJson, type)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList<String>()
        }
    }
    if (allergies.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Warning, contentDescription = "Allergies", tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Allergies", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    allergies.forEach { allergy ->
                        Chip(text = allergy, color = Color(0xFFFFEBEE), textColor = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun InsuranceCard(patient: PatientEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Shield, contentDescription = "Insurance", tint = Color.Blue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Insurance Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            InfoRow("Provider", patient.insuranceProvider)
            InfoRow("Policy Number", patient.policyNumber)
            InfoRow("Valid Until", patient.validity)
        }
    }
}


@Composable
fun LifestyleCard(patient: PatientEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.TrendingUp, contentDescription = "Lifestyle", tint = Color.Magenta)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Lifestyle", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            LifestyleRow("Smoking", patient.smoking)
            LifestyleRow("Alcohol", patient.alcohol)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        Text(value, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun LifestyleRow(label: String, value: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label)
        Chip(text = if (value) "Yes" else "No", color = if (value) Color.Red.copy(alpha = 0.1f) else Color.Green.copy(alpha = 0.1f), textColor = if (value) Color.Red else Color.Green)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun VitalSign(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun Chip(text: String, color: Color, textColor: Color) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = color)) {
        Text(text = text, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontSize = 12.sp, color = textColor)
    }
}
