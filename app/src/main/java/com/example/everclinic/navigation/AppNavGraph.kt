
/**
 * This file defines the navigation graph for the entire application.
 * It uses Jetpack Navigation Compose to define all the screens and their routes.
 */
package com.example.everclinic.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.everclinic.ui.screens.DashboardScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    showSnackbar: (String) -> Unit
) {
    NavHost(navController = navController, startDestination = Routes.DASHBOARD) {
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        // Other screens will be added here
    }
}
