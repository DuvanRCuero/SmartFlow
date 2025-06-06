package com.example.smartflow.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class HomeTab(val label: String, val icon: ImageVector) {
    Chat("Chat", Icons.Filled.Chat),
    Calendar("Calendar", Icons.Filled.CalendarToday),
    Tasks("Tasks", Icons.Filled.List),
    Productivity("Productivity", Icons.Filled.BarChart)
}