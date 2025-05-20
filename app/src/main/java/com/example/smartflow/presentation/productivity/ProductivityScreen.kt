package com.example.smartflow.presentation.productivity

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
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCheckmarkTaskComponents
import com.example.smartflow.presentation.theme.*

@Composable
fun ProductivityScreen() {
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

            // Productivity time blocks
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Morning
                item {
                    TimeBlockCard(
                        title = "Morning",
                        timeRange = "9:00 AM - 12:00 PM",
                        icon = "morning_icon" // In real app, use actual resource ID
                    )
                }

                // Afternoon
                item {
                    TimeBlockCard(
                        title = "Afternoon",
                        timeRange = "12:00 PM - 5:00 PM",
                        icon = "sun_icon" // In real app, use actual resource ID
                    )
                }

                // Evening
                item {
                    TimeBlockCard(
                        title = "Evening",
                        timeRange = "5:00 PM - 9:00 PM",
                        icon = "evening_icon" // In real app, use actual resource ID
                    )
                }

                // Night
                item {
                    TimeBlockCard(
                        title = "Night",
                        timeRange = "9:00 PM - 10:00 PM",
                        icon = "moon_icon" // In real app, use actual resource ID
                    )
                }

                // Late Night
                item {
                    TimeBlockCard(
                        title = "Late Night",
                        timeRange = "10:00 PM - 12:00 AM",
                        icon = "late_night_icon" // In real app, use actual resource ID
                    )
                }

                // Productivity Tips
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        ProductivityTipCard(
                            tip = "You avoid complex tasks after lunch",
                            details = "Complex tasks: 0/8"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ProductivityTipCard(
                            tip = "Task completion rate: 80% in mornings",
                            details = "Mornings: 9:00 AM - 12:00 PM"
                        )
                    }
                }
            }
        }
    }
}
