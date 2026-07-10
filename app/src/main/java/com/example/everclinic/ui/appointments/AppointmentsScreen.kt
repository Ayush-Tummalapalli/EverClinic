package com.example.everclinic.ui.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.everclinic.data.local.relations.AppointmentDetails
import com.example.everclinic.navigation.Screen
import com.example.everclinic.ui.ViewModelFactory

@Composable
fun AppointmentsScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as EverclinicApplication
    val patientDao = app.database.patientDao()
    val doctorDao = app.database.doctorDao()
    val treatmentDao = app.database.treatmentDao()
    val appointmentDao = app.database.appointmentDao()
    val viewModel: AppointmentsViewModel = viewModel(factory = ViewModelFactory(patientDao, doctorDao, appointmentDao, treatmentDao))

    val appointments by viewModel.appointments.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    val upcomingAppointments = appointments.filter { it.appointment.status == "Scheduled" }
    val pastAppointments = appointments.filter { it.appointment.status != "Scheduled" }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddAppointment.route) },
                containerColor = Color(0xFF2962FF),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Appointment")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            AppointmentsHeader(navController)
            SearchBar(searchQuery, onQueryChange = viewModel::onSearchQueryChange)
            AppointmentTabs(selectedTab, onTabSelected = { selectedTab = it }, upcomingCount = upcomingAppointments.size, pastCount = pastAppointments.size)
            AppointmentList(if (selectedTab == 0) upcomingAppointments else pastAppointments, navController)
        }
    }
}

@Composable
fun AppointmentsHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF4DB6AC),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Text(
            text = "Appointments",
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
        placeholder = { Text("Search appointments...") },
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
fun AppointmentTabs(selectedTab: Int, onTabSelected: (Int) -> Unit, upcomingCount: Int, pastCount: Int) {
    TabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color(0xFF4DB6AC),
        contentColor = Color.White,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Tab(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            text = { Text("Upcoming ($upcomingCount)") },
            modifier = Modifier.padding(12.dp)
        )
        Tab(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            text = { Text("Past ($pastCount)") },
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun AppointmentList(appointments: List<AppointmentDetails>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(appointments) { appointment ->
            AppointmentCard(appointment, navController)
        }
    }
}

@Composable
fun AppointmentCard(appointment: AppointmentDetails, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.AppointmentDetail.createRoute(appointment.appointment.id)) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF81D4FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = appointment.patientName.take(2).uppercase(),
                        color = Color(0xFF01579B),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = appointment.patientName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = appointment.doctorName, color = Color.Gray, fontSize = 14.sp)
                }
            }
            Text(
                text = appointment.appointment.reason,
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.CalendarToday, contentDescription = "Date", tint = Color.Gray, modifier = Modifier.size(16.dp))
                Text(
                    text = appointment.appointment.date,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Icon(Icons.Filled.CalendarToday, contentDescription = "Time", tint = Color.Gray, modifier = Modifier.size(16.dp).padding(start = 8.dp))
                Text(
                    text = appointment.appointment.time,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Text(
                text = appointment.appointment.status,
                color = if (appointment.appointment.status == "Scheduled") Color(0xFF4CAF50) else Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(
                        if (appointment.appointment.status == "Scheduled") Color(0xFFC8E6C9) else Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}
