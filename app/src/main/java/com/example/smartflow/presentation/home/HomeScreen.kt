package com.example.smartflow.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/** Main home screen with top bar, bottom nav, and scrollable content. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
    onChatClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onTasksClick: () -> Unit,
    onProductivityClick: () -> Unit,
    onBottomNavSelect: (HomeTab) -> Unit,
    selectedTab: HomeTab
) {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "¡Hello, $userName!",
                        color = colors.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            ) {
                HomeTab.values().forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        selected = tab == selectedTab,
                        onClick = { onBottomNavSelect(tab) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor   = colors.onPrimary,
                            unselectedIconColor = colors.onPrimary.copy(alpha = 0.6f),
                            selectedTextColor   = colors.onPrimary,
                            unselectedTextColor = colors.onPrimary.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        },
        containerColor = colors.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(colors.background)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickCard(
                    "Chat",
                    Icons.Filled.Chat,
                    modifier = Modifier.weight(1f),
                    onClick = onChatClick,
                    cardColor = colors.surface,
                    contentColor = colors.onSurface
                )
                QuickCard(
                    "Calendario",
                    Icons.Filled.CalendarToday,
                    modifier = Modifier.weight(1f),
                    onClick = onCalendarClick,
                    cardColor = colors.surface,
                    contentColor = colors.onSurface
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickCard(
                    "Tareas",
                    Icons.Filled.List,
                    modifier = Modifier.weight(1f),
                    onClick = onTasksClick,
                    cardColor = colors.surface,
                    contentColor = colors.onSurface
                )
                QuickCard(
                    "Productividad",
                    Icons.Filled.BarChart,
                    modifier = Modifier.weight(1f),
                    onClick = onProductivityClick,
                    cardColor = colors.surface,
                    contentColor = colors.onSurface
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Tareas completadas",
                style = MaterialTheme.typography.titleMedium,
                color = colors.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Card(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "64",
                        style = MaterialTheme.typography.headlineLarge,
                        color = colors.primary,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "+2% vs 5 semanas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Próximas reuniones",
                style = MaterialTheme.typography.titleMedium,
                color = colors.onBackground
            )
            Spacer(Modifier.height(8.dp))
            UpcomingItem(
                title = "Proyecto Kick-off",
                time = "10:00 AM - 11:00 AM",
                cardColor = colors.surface,
                contentColor = colors.onSurface
            )
            UpcomingItem(
                title = "Sprint Review",
                time = "2:00 PM - 3:00 PM",
                cardColor = colors.surface,
                contentColor = colors.onSurface
            )
        }
    }
}

/** A tappable card with an icon and label. */
@Composable
private fun QuickCard(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    cardColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement   = Arrangement.Center,
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )
        }
    }
}

/** A single upcoming‐item row with title/time and an arrow icon. */
@Composable
private fun UpcomingItem(
    title: String,
    time: String,
    cardColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, color = contentColor)
                Text(time,  style = MaterialTheme.typography.bodySmall, color = contentColor)
            }
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = "Detalles",
                tint = colors.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/** Tabs for the bottom NavigationBar. */
enum class HomeTab(val label: String, val icon: ImageVector) {
    Chat        ("Chat",        Icons.Filled.Chat),
    Calendar    ("Calendario",  Icons.Filled.CalendarToday),
    Tasks       ("Tareas",      Icons.Filled.List),
    Productivity("Productividad", Icons.Filled.BarChart)
}
