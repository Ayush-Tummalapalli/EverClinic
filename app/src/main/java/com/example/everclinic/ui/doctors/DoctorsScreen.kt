package com.example.everclinic.ui.doctors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.example.everclinic.data.local.entities.DoctorEntity
import com.example.everclinic.navigation.Screen
import com.example.everclinic.ui.ViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun DoctorsScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val doctorDao = app.database.doctorDao()
    val patientDao = app.database.patientDao()
    val appointmentDao = app.database.appointmentDao()
    val treatmentDao = app.database.treatmentDao()
    val viewModel: DoctorsViewModel = viewModel(factory = ViewModelFactory(patientDao, doctorDao, appointmentDao, treatmentDao))
    val doctors by viewModel.doctors.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddDoctor.route) },
                containerColor = Color(0xFF1E88E5),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Doctor")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            DoctorsHeader()
            SearchBar(searchQuery, onQueryChange = viewModel::onSearchQueryChange)
            DoctorList(doctors, navController)
        }
    }
}

@Composable
fun DoctorsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF4CAF50),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
        Text(
            text = "Doctors",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search doctors by name or specialty...") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun DoctorList(doctors: List<DoctorEntity>, navController: NavController) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "${doctors.size} doctors found",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(doctors) { doctor ->
                DoctorCard(doctor, navController)
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: DoctorEntity, navController: NavController) {
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
            .clickable { navController.navigate(Screen.DoctorDetail.createRoute(doctor.id)) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFA5D6A7)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = doctor.name.take(2).uppercase(),
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = doctor.specialty, color = Color.Gray, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Icon(Icons.Filled.Work, contentDescription = "Experience", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Text(
                        text = "${doctor.experience} years experience",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    availability.forEach {
                        Text(
                            text = it,
                            color = Color(0xFF1B5E20),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .background(Color(0xFFC8E6C9), shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
