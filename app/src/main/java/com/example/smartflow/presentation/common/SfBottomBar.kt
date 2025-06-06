// SfBottomBar.kt
package com.example.smartflow.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartflow.presentation.navigation.SfDestination
import com.example.smartflow.ui.theme.SmartFlowButtonBlue
import com.example.smartflow.ui.theme.White

@Composable
fun SfBottomBar(
    selected: SfDestination,
    onDestinationClick: (SfDestination) -> Unit = {}
) {
    NavigationBar(
        containerColor = White.copy(alpha = 0.1f)
    ) {
        SfDestination.entries.forEach { destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = getIconForDestination(destination, selected == destination),
                        contentDescription = destination.label
                    )
                },
                label = { Text(destination.label) },
                selected = selected == destination,
                onClick = { onDestinationClick(destination) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SmartFlowButtonBlue,
                    selectedTextColor = SmartFlowButtonBlue,
                    unselectedIconColor = White.copy(alpha = 0.6f),
                    unselectedTextColor = White.copy(alpha = 0.6f),
                    indicatorColor = SmartFlowButtonBlue.copy(alpha = 0.2f)
                )
            )
        }
    }
}

private fun getIconForDestination(destination: SfDestination, isSelected: Boolean): ImageVector {
    return when (destination) {
        SfDestination.Home -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
        SfDestination.Chat -> if (isSelected) Icons.Filled.Chat else Icons.Outlined.Chat
        SfDestination.Calendar -> if (isSelected) Icons.Filled.CalendarToday else Icons.Outlined.CalendarToday
        SfDestination.Tasks -> if (isSelected) Icons.Filled.Task else Icons.Outlined.Task
        SfDestination.Productivity -> if (isSelected) Icons.Filled.Analytics else Icons.Outlined.Analytics
    }
}