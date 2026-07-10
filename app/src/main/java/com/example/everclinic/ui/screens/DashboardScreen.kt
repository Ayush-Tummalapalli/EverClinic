
/**
 * This file contains the DashboardScreen composable, which is the main screen of the application.
 * It displays a summary of the clinic's data.
 * Mapped from Dashboard.tsx.
 */
package com.example.everclinic.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.everclinic.ui.components.HeaderCard
import com.example.everclinic.ui.components.QuickAccessCard
import com.example.everclinic.ui.theme.EverclinicTheme
import com.example.everclinic.ui.theme.PurpleEnd
import com.example.everclinic.ui.theme.PurpleStart

@Composable
fun DashboardScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        HeaderCard(
            title = "Dashboard",
            subtitle = "Welcome to Everclinic",
            gradientColors = listOf(PurpleStart, PurpleEnd)
        ) {
            Column {
                QuickAccessCard(
                    icon = Icons.Default.People, title = "Patients", count = 12, color = PurpleStart
                )
                QuickAccessCard(
                    icon = Icons.Default.MedicalServices, title = "Doctors", count = 4, color = PurpleStart
                )
                QuickAccessCard(
                    icon = Icons.Default.CalendarToday, title = "Appointments", count = 8, color = PurpleStart
                )
                QuickAccessCard(
                    icon = Icons.Default.Thermostat, title = "Treatments", count = 23, color = PurpleStart
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    EverclinicTheme {
        DashboardScreen(navController = rememberNavController(), paddingValues = PaddingValues())
    }
}
