package com.example.smartflow.presentation.navigation

sealed class Screen(val route: String) {
    object Login        : Screen("login")
    object Signup       : Screen("signup")
    object Dashboard    : Screen("dashboard")
    object Chat         : Screen("chat")
    object Calendar     : Screen("calendar")
    object Productivity : Screen("productivity")
    object Task         : Screen("task")
}
