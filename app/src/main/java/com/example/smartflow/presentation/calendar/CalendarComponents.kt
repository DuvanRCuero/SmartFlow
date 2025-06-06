package com.example.smartflow.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.theme.*

@Composable
fun CalendarView(
    monthProgress: Float,
    weekProgress: Float,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    firstDayOfWeek: Int    // <— NEW parameter: 0 = Sunday, 1 = Monday, …, 6 = Saturday
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Month progress indicator
        LinearProgressIndicator(
            progress = monthProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = SmartFlowButtonBlue,
            trackColor = White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weekday headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Adjust labels if you want the first day to be Monday instead of Sunday.
            // For simplicity, this example just hardcodes Sun–Sat:
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val daysInMonth = 31
            val firstDayOfMonth = 3 // Example: 0 = Sunday, 1 = Monday, ..., 6 = Saturday

            // Compute how many blank cells to show before “1”
            val startOffset = (firstDayOfMonth - firstDayOfWeek + 7) % 7

            // Empty cells for offset
            items(startOffset) {
                Box(modifier = Modifier.size(40.dp))
            }

            // Days of the month
            items(daysInMonth) { index ->
                val currentDay = index + 1
                val isSelected = currentDay == selectedDay

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) SmartFlowButtonBlue else Color.Transparent)
                        .clickable { onDaySelected(currentDay) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentDay.toString(),
                        color = White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Week progress indicator
        LinearProgressIndicator(
            progress = weekProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = SmartFlowButtonBlue,
            trackColor = White
        )
    }
}


@Composable
fun EventItem(
    title: String,
    time: String,
    isAlert: Boolean = false
) {
    SmartFlowCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )
            }

            if (!isAlert) {
                IconButton(
                    onClick = { /* Add functionality */ },
                    modifier = Modifier
                        .size(32.dp)
                        .background(SmartFlowTeal, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(SmartFlowButtonBlue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "!",
                        color = White
                    )
                }
            }
        }
    }
}