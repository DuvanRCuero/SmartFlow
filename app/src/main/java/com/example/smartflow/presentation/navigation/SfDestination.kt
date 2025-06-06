package com.example.smartflow.presentation.navigation

/**
 * Cada destino tiene:
 * - route  → String utilizado por Navigation-Compose
 * - label  → Texto breve para la BottomBar
 */
enum class SfDestination(val route: String, val label: String) {
    Home("home", "Home"),
    Chat("chat", "Chat"),
    Calendar("calendar", "Calendar"),
    Tasks("tasks", "Tasks"),
    Productivity("productivity", "Productivity");
}