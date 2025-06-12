// Updated AppNavigation.kt
package com.example.smartflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.presentation.login.LoginScreen
import com.example.smartflow.presentation.auth.SignupScreen
import com.example.smartflow.presentation.calendar.CalendarScreen
import com.example.smartflow.presentation.chat.ChatScreen
import com.example.smartflow.presentation.home.HomeScreen
import com.example.smartflow.presentation.productivity.ProductivityScreen
import com.example.smartflow.presentation.task.TaskScreen

@Composable
fun AppNavigation(
    authRepository: AuthRepository,
    navController: NavHostController = rememberNavController()
) {
    /* ➊ Escucha el estado de sesión (Flow→State) */
    val isLoggedIn by authRepository
        .isLoggedIn()              // Flow<Boolean>
        .collectAsState(initial = false)

    /* ➋ Ruta inicial dinámica */
    val startDestination = if (isLoggedIn) SfDestination.Home.route
    else AuthDestination.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        /* -------- Auth Screens -------- */
        composable(AuthDestination.Login.route) {
            LoginScreen(
                onSuccess = {
                    navController.navigate(SfDestination.Home.route) {
                        popUpTo(AuthDestination.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(AuthDestination.Signup.route)
                }
            )
        }

        composable(AuthDestination.Signup.route) {
            SignupScreen(
                onSuccess = {
                    navController.navigate(SfDestination.Home.route) {
                        popUpTo(AuthDestination.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        /* -------- Main App Screens -------- */
        composable(SfDestination.Home.route) {
            HomeScreen(
                onSelectBottom = { destination ->
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(SfDestination.Chat.route) {
            ChatScreen(
                onNavigateBack = { navController.popBackStack() },
                onSelectBottom = { destination ->
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(SfDestination.Calendar.route) {
            CalendarScreen(onBack = { navController.popBackStack() })
        }

        composable(SfDestination.Tasks.route) {
            TaskScreen(
                onBack = { navController.popBackStack() },
                onSelectBottom = { destination ->
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(SfDestination.Productivity.route) {
            ProductivityScreen(
                onBack = { navController.popBackStack() },
                onSelectBottom = { destination ->
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
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