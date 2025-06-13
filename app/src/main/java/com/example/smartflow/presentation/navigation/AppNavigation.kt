package com.example.smartflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartflow.presentation.calendar.CalendarScreen
import com.example.smartflow.presentation.home.HomeScreen
import com.example.smartflow.presentation.productivity.ProductivityScreen
import com.example.smartflow.presentation.task.TaskScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SfDestination.Home.route
    ) {
        // Home Screen (Landing page)
        composable(SfDestination.Home.route) {
            HomeScreen(
                onSelectBottom = { destination: SfDestination ->
                    navController.navigate(destination.route) {
                        popUpTo(SfDestination.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToTasks = {
                    navController.navigate(SfDestination.Tasks.route)
                },
                onNavigateToCalendar = {
                    navController.navigate(SfDestination.Calendar.route)
                },
                onNavigateToProductivity = {
                    navController.navigate(SfDestination.Productivity.route)
                }
            )
        }

        // Tasks Screen
        composable(SfDestination.Tasks.route) {
            TaskScreen(
                onBack = {
                    navController.navigateUp()
                },
                onSelectBottom = { destination: SfDestination ->
                    navController.navigate(destination.route) {
                        popUpTo(SfDestination.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        // Calendar Screen
        composable(SfDestination.Calendar.route) {
            CalendarScreen(
                onBack = {
                    navController.navigateUp()
                },
                onSelectBottom = { destination: SfDestination ->
                    navController.navigate(destination.route) {
                        popUpTo(SfDestination.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        // Productivity Screen
        composable(SfDestination.Productivity.route) {
            ProductivityScreen(
                onBack = {
                    navController.navigateUp()
                },
                onSelectBottom = { destination: SfDestination ->
                    navController.navigate(destination.route) {
                        popUpTo(SfDestination.Home.route) {
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