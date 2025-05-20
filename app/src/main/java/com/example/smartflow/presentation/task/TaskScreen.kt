package com.example.smartflow.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartflow.presentation.common.SmartFlowCheckmark
import com.example.smartflow.presentation.theme.*

@Composable
fun TaskScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with back button and checkmark
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Navigate back */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                SmartFlowCheckmark(size = 32)
            }

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
                    text = "64",
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
                        text = "+2%",
                        color = PeakEnergy,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Tasks chart
            TaskCompletionChart()

            // Task list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(2) { index ->
                    if (index == 0) {
                        TaskItem(
                            title = "Schedule deep work at 10 AM",
                            subtitle = "your focus peak",
                            showAddButton = true
                        )
                    } else {
                        TaskItem(
                            title = "Focus on meetings after lunch",
                            subtitle = "your non-productive time",
                            showAddButton = true
                        )
                    }
                }
            }
        }
    }
}
