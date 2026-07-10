
/**
 * This file contains the BottomNavBar composable, which is the bottom navigation bar
 * for the Everclinic application.
 * Mapped from BottomNavigation.tsx.
 */
package com.example.everclinic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.everclinic.navigation.Routes

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            isSelected = currentRoute == Routes.DASHBOARD,
            onClick = { navController.navigate(Routes.DASHBOARD) }
        )
        BottomNavItem(
            icon = Icons.Default.People,
            label = "Patients",
            isSelected = currentRoute == Routes.PATIENTS,
            onClick = { navController.navigate(Routes.PATIENTS) }
        )
        BottomNavItem(
            icon = Icons.Default.MedicalServices,
            label = "Doctors",
            isSelected = currentRoute == Routes.DOCTORS,
            onClick = { navController.navigate(Routes.DOCTORS) }
        )
        BottomNavItem(
            icon = Icons.Default.CalendarToday,
            label = "Appointments",
            isSelected = currentRoute == Routes.APPOINTMENTS,
            onClick = { navController.navigate(Routes.APPOINTMENTS) }
        )
        BottomNavItem(
            icon = Icons.Default.Thermostat,
            label = "Treatments",
            isSelected = currentRoute == Routes.TREATMENTS,
            onClick = { navController.navigate(Routes.TREATMENTS) }
        )
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background = if (isSelected) {
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
            ),
            shape = CircleShape
        )
    } else {
        Modifier
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .padding(8.dp)
            .then(background)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color.White else Color.Gray
        )
        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Gray
        )
    }
}
