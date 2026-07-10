package com.example.everclinic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.everclinic.navigation.Screen
import com.example.everclinic.ui.appointments.AddAppointmentScreen
import com.example.everclinic.ui.appointments.AppointmentDetailScreen
import com.example.everclinic.ui.appointments.AppointmentsScreen
import com.example.everclinic.ui.appointments.EditAppointmentScreen
import com.example.everclinic.ui.doctors.AddDoctorScreen
import com.example.everclinic.ui.doctors.DoctorDetailScreen
import com.example.everclinic.ui.doctors.DoctorsScreen
import com.example.everclinic.ui.doctors.EditDoctorScreen
import com.example.everclinic.ui.home.HomeScreen
import com.example.everclinic.ui.patients.AddPatientScreen
import com.example.everclinic.ui.patients.EditPatientScreen
import com.example.everclinic.ui.patients.PatientDetailScreen
import com.example.everclinic.ui.patients.PatientsScreen
import com.example.everclinic.ui.theme.EverclinicTheme
import com.example.everclinic.ui.treatments.AddTreatmentScreen
import com.example.everclinic.ui.treatments.TreatmentDetailScreen
import com.example.everclinic.ui.treatments.TreatmentsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EverclinicTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        NavigationBar {
                            val items = listOf(
                                Screen.Home,
                                Screen.Patients,
                                Screen.Doctors,
                                Screen.Appointments,
                                Screen.Treatments
                            )
                            items.forEach { screen ->
                                NavigationBarItem(
                                    icon = {
                                        when (screen) {
                                            Screen.Home -> Icon(Icons.Filled.Home, contentDescription = "Home")
                                            Screen.Patients -> Icon(Icons.Filled.People, contentDescription = "Patients")
                                            Screen.Doctors -> Icon(Icons.Filled.Person, contentDescription = "Doctors")
                                            Screen.Appointments -> Icon(Icons.Filled.CalendarToday, contentDescription = "Appointments")
                                            Screen.Treatments -> Icon(Icons.Filled.Description, contentDescription = "Treatments")
                                            else -> {}
                                        }
                                    },
                                    label = { Text(screen.route.replaceFirstChar { it.uppercase() }, softWrap = false, fontSize = 11.sp) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Home.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        composable(Screen.Patients.route) { PatientsScreen(navController) }
                        composable(Screen.Doctors.route) { DoctorsScreen(navController) }
                        composable(Screen.Appointments.route) { AppointmentsScreen(navController) }
                        composable(Screen.Treatments.route) { TreatmentsScreen(navController) }
                        composable(Screen.AddPatient.route) { AddPatientScreen(navController) }
                        composable(Screen.AddDoctor.route) { AddDoctorScreen(navController) }
                        composable(Screen.AddAppointment.route) { AddAppointmentScreen(navController) }
                        composable(Screen.AddTreatment.route) { AddTreatmentScreen(navController) }
                        composable(
                            Screen.PatientDetail.route,
                            arguments = listOf(navArgument("patientId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            PatientDetailScreen(backStackEntry.arguments?.getString("patientId") ?: "", navController)
                        }
                        composable(
                            Screen.DoctorDetail.route,
                            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            DoctorDetailScreen(backStackEntry.arguments?.getString("doctorId") ?: "", navController)
                        }
                        composable(
                            Screen.AppointmentDetail.route,
                            arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            AppointmentDetailScreen(backStackEntry.arguments?.getString("appointmentId") ?: "", navController)
                        }
                        composable(
                            Screen.TreatmentDetail.route,
                            arguments = listOf(navArgument("treatmentId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            TreatmentDetailScreen(backStackEntry.arguments?.getString("treatmentId") ?: "", navController)
                        }
                        composable(
                            Screen.EditAppointment.route,
                            arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            EditAppointmentScreen(backStackEntry.arguments?.getString("appointmentId") ?: "", navController)
                        }
                        composable(
                            Screen.EditDoctor.route,
                            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            EditDoctorScreen(backStackEntry.arguments?.getString("doctorId") ?: "", navController)
                        }
                        composable(
                            Screen.EditPatient.route,
                            arguments = listOf(navArgument("patientId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            EditPatientScreen(backStackEntry.arguments?.getString("patientId") ?: "", navController)
                        }
                    }
                }
            }
        }
    }
}