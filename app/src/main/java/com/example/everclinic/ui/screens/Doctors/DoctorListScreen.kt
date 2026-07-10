
/**
 * This file contains the DoctorListScreen composable, which displays a list of doctors.
 * Mapped from DoctorList.tsx.
 */
package com.example.everclinic.ui.screens.Doctors

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
fun DoctorListScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ADD_DOCTOR) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Doctor")
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(5) { index ->
                    EntityListItem(
                        title = "Doctor Name $index",
                        subtitle1 = "Specialty",
                        subtitle2 = "Experience: 10 years",
                        dateText = "",
                        onClick = { navController.navigate("doctor_profile/$index") }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorListScreenPreview() {
    EverclinicTheme {
        DoctorListScreen(navController = rememberNavController(), paddingValues = PaddingValues())
    }
}
