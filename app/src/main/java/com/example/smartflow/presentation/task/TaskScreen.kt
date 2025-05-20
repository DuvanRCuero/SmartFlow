package com.example.smartflow.presentation.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartflow.presentation.common.ScreenScaffold
import com.example.smartflow.presentation.theme.*

@Composable
fun TaskScreen(
    onBackClick: () -> Unit = {},
    taskViewModel: TaskViewModel = viewModel()
) {
    val tasksCompleted by taskViewModel.tasksCompleted.collectAsState()
    val tasksCompletedChangePercent by taskViewModel.tasksCompletedChangePercent.collectAsState()
    val weeklyTasksData by taskViewModel.weeklyTasksData.collectAsState()
    val suggestedTasks by taskViewModel.suggestedTasks.collectAsState()

    ScreenScaffold(
        title = "Tasks",
        onBackClick = onBackClick
    ) {
        // Tasks completed counter
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Tasks Completed",
                color = White,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = tasksCompleted.toString(),
                color = White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Past 5 weeks",
                    color = White,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = tasksCompletedChangePercent,
                    color = PeakEnergy,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Tasks chart
        TaskCompletionChart(weeklyTasksData = weeklyTasksData)

        // Task list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(suggestedTasks) { task ->
                TaskItem(
                    title = task.title,
                    subtitle = task.subtitle,
                    showAddButton = true
                )
            }
        }
    }
}