
/**
 * This file contains the TreatmentHistoryScreen composable, which displays a list of treatments.
 * Mapped from TreatmentHistory.tsx.
 */
package com.example.everclinic.ui.screens.Treatments

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
fun TreatmentHistoryScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ADD_TREATMENT) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Treatment")
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(15) { index ->
                    EntityListItem(
                        title = "Patient Name",
                        subtitle1 = "Diagnosis",
                        subtitle2 = "Treatment",
                        dateText = "2023-10-27",
                        onClick = { navController.navigate("edit_treatment/$index") }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TreatmentHistoryScreenPreview() {
    EverclinicTheme {
        TreatmentHistoryScreen(navController = rememberNavController(), paddingValues = PaddingValues())
    }
}
