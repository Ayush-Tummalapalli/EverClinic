
/**
 * This file contains the AppointmentListScreen composable, which displays a list of appointments.
 * Mapped from AppointmentList.tsx.
 */
package com.example.everclinic.ui.screens.Appointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.everclinic.navigation.Routes
import com.example.everclinic.ui.components.EntityListItem
import com.example.everclinic.ui.theme.EverclinicTheme

@Composable
fun AppointmentListScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ADD_APPOINTMENT) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Appointment")
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(8) { index ->
                    EntityListItem(
                        title = "Patient Name",
                        subtitle1 = "with Dr. Doctor Name",
                        subtitle2 = "Reason for visit",
                        dateText = "10:00 AM",
                        onClick = { navController.navigate("appointment_details/$index") }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentListScreenPreview() {
    EverclinicTheme {
        AppointmentListScreen(navController = rememberNavController(), paddingValues = PaddingValues())
    }
}
