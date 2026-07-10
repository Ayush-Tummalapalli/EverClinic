
/**
 * This file contains the PatientListScreen composable, which displays a list of patients.
 * Mapped from PatientList.tsx.
 */
package com.example.everclinic.ui.screens.Patients

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
fun PatientListScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ADD_PATIENT) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Patient")
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(10) { index ->
                    EntityListItem(
                        title = "Patient Name $index",
                        subtitle1 = "Condition",
                        subtitle2 = "Last Visit: 2023-10-27",
                        dateText = "27 Oct",
                        onClick = { navController.navigate("patient_profile/$index") }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PatientListScreenPreview() {
    EverclinicTheme {
        PatientListScreen(navController = rememberNavController(), paddingValues = PaddingValues())
    }
}
