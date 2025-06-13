package com.example.smartflow.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.common.SfBottomBar
import com.example.smartflow.presentation.navigation.SfDestination
import com.example.smartflow.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSelectBottom: (SfDestination) -> Unit,
    onNavigateToTasks: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {},
    onNavigateToProductivity: () -> Unit = {},
    vm: HomeViewModel = viewModel()
) {
    val uiState by vm.ui.collectAsState()

    Scaffold(
        bottomBar = {
            SfBottomBar(
                selected = SfDestination.Home,
                onDestinationClick = onSelectBottom
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Welcome Header
            item {
                WelcomeHeader(userName = uiState.userName)
            }

            // Dashboard Overview Card
            item {
                DashboardOverviewCard(
                    onNavigateToProductivity = onNavigateToProductivity
                )
            }

            // Today's Tasks Summary
            item {
                TodaysTasksCard(
                    completedTasks = uiState.completedTasks,
                    pendingTasks = uiState.pendingTasks,
                    onNavigateToTasks = onNavigateToTasks
                )
            }

            // Recent Tasks Section
            item {
                Text(
                    text = "Recent Tasks",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            if (uiState.recentTasks.isNotEmpty()) {
                items(uiState.recentTasks) { task ->
                    RecentTaskItem(
                        task = task,
                        onTaskClick = { /* Navigate to task details */ }
                    )
                }
            } else {
                item {
                    EmptyTasksCard(onNavigateToTasks = onNavigateToTasks)
                }
            }

            // Quick Actions (Optional)
            item {
                QuickActionsCard(
                    onNavigateToTasks = onNavigateToTasks,
                    onNavigateToCalendar = onNavigateToCalendar,
                    onNavigateToProductivity = onNavigateToProductivity
                )
            }
        }
    }
}

@Composable
private fun WelcomeHeader(userName: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun DashboardOverviewCard(
    onNavigateToProductivity: () -> Unit
) {
    SmartFlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToProductivity() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = SmartFlowButtonBlue.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Analytics,
                        contentDescription = null,
                        tint = SmartFlowButtonBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "SmartFlow Dashboard",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Your productivity overview",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun TodaysTasksCard(
    completedTasks: Int,
    pendingTasks: Int,
    onNavigateToTasks: () -> Unit
) {
    SmartFlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToTasks() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Today's Tasks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TaskStatusItem(
                    count = completedTasks,
                    label = "Completed",
                    icon = Icons.Outlined.CheckCircle,
                    color = SmartFlowTeal
                )

                TaskStatusItem(
                    count = pendingTasks,
                    label = "Pending",
                    icon = Icons.Outlined.Schedule,
                    color = SmartFlowButtonBlue
                )
            }
        }
    }
}

@Composable
private fun TaskStatusItem(
    count: Int,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = color.copy(alpha = 0.1f),
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun RecentTaskItem(
    task: Task,
    onTaskClick: () -> Unit
) {
    SmartFlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = if (task.isCompleted) SmartFlowTeal.copy(alpha = 0.1f)
                else SmartFlowButtonBlue.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (task.isCompleted) Icons.Outlined.CheckCircle
                        else Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = if (task.isCompleted) SmartFlowTeal else SmartFlowButtonBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (task.description.isNotEmpty()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Text(
                text = task.timeAgo,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun EmptyTasksCard(
    onNavigateToTasks: () -> Unit
) {
    SmartFlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToTasks() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = SmartFlowTeal.copy(alpha = 0.1f),
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        tint = SmartFlowTeal,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Text(
                text = "No tasks yet. Create your first task!",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Tap the Tasks tab to get started",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun QuickActionsCard(
    onNavigateToTasks: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToProductivity: () -> Unit
) {
    SmartFlowCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton(
                    text = "Add Task",
                    onClick = onNavigateToTasks,
                    modifier = Modifier.weight(1f)
                )

                QuickActionButton(
                    text = "Calendar",
                    onClick = onNavigateToCalendar,
                    modifier = Modifier.weight(1f)
                )

                QuickActionButton(
                    text = "Analytics",
                    onClick = onNavigateToProductivity,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun QuickActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = SmartFlowButtonBlue
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.ui.graphics.SolidColor(SmartFlowButtonBlue.copy(alpha = 0.5f))
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}