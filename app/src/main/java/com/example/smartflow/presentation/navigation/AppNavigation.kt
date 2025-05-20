package com.example.smartflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.presentation.auth.LoginScreen
import com.example.smartflow.presentation.auth.SignupScreen
import com.example.smartflow.presentation.chat.ChatScreen
import com.example.smartflow.presentation.calendar.CalendarScreen
import com.example.smartflow.presentation.task.TaskScreen
import com.example.smartflow.presentation.productivity.ProductivityScreen
import com.example.smartflow.presentation.home.HomeScreen
import com.example.smartflow.presentation.home.HomeTab
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    authRepository: AuthRepository,
    navController: NavHostController = rememberNavController()
) {
    val scope = rememberCoroutineScope()
    val isLoggedIn by authRepository.isLoggedIn().collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Dashboard.route else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack(Screen.Login.route, false)
                }
            )
        }

        // Dashboard with HomeScreen + logout
        composable(Screen.Dashboard.route) {
            // Determine active tab:
            val navBackStack by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStack?.destination?.route
            val selectedTab = when (currentRoute) {
                Screen.Chat.route -> HomeTab.Chat
                Screen.Calendar.route -> HomeTab.Calendar
                Screen.Productivity.route -> HomeTab.Productivity
                Screen.Task.route -> HomeTab.Tasks
                else -> HomeTab.Tasks
            }

            HomeScreen(
                userName = "User", // Changed to English for consistency
                onChatClick = { navController.navigate(Screen.Chat.route) },
                onCalendarClick = { navController.navigate(Screen.Calendar.route) },
                onTasksClick = { navController.navigate(Screen.Task.route) },
                onProductivityClick = { navController.navigate(Screen.Productivity.route) },
                onBottomNavSelect = { tab ->
                    when (tab) {
                        HomeTab.Chat -> navController.navigate(Screen.Chat.route)
                        HomeTab.Calendar -> navController.navigate(Screen.Calendar.route)
                        HomeTab.Tasks -> navController.navigate(Screen.Task.route)
                        HomeTab.Productivity -> navController.navigate(Screen.Productivity.route)
                    }
                },
                selectedTab = selectedTab,
                onLogoutClick = {
                    scope.launch {
                        authRepository.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Dashboard.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Chat.route) {
            ChatScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.Calendar.route) {
            CalendarScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.Productivity.route) {
            ProductivityScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.Task.route) {
            TaskScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}