package com.example.smartflow.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.ui.theme.*

@Composable
fun CalendarView(
    monthProgress: Float,
    weekProgress: Float,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    firstDayOfWeek: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LinearProgressIndicator(
            progress = monthProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = SmartFlowButtonBlue,
            trackColor = White
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = White
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val daysInMonth = 31
            val firstDayOfMonth = 3

            val offset = (firstDayOfMonth - firstDayOfWeek + 7) % 7
            items(offset) { Box(Modifier.size(40.dp)) }

            items(daysInMonth) { index ->
                val day = index + 1
                val isSelected = day == selectedDay

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) SmartFlowButtonBlue else Color.Transparent)
                        .clickable { onDaySelected(day) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(day.toString(), color = White)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

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
    SmartFlowCard(Modifier.fillMaxWidth()) {
        EventItemRow(title, time, isAlert)
    }
}

@Composable
fun EventItemRow(
    title: String,
    time: String,
    isAlert: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Event indicator dot
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = if (isAlert) MaterialTheme.colorScheme.error else SmartFlowTeal,
                    shape = CircleShape
                )
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        if (!isAlert) {
            IconButton(
                onClick = { /* TODO: Add action */ },
                modifier = Modifier
                    .size(32.dp)
                    .background(SmartFlowTeal, CircleShape)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        } else {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.error
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Mini calendar component for use in other screens
@Composable
fun MiniCalendarCard(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SmartFlowCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Calendar",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Simplified week view
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                (15..21).forEach { day ->
                    val isSelected = day == selectedDay
                    Surface(
                        modifier = Modifier
                            .size(32.dp),
                        shape = CircleShape,
                        color = if (isSelected) SmartFlowButtonBlue else Color.Transparent,
                        onClick = { onDaySelected(day) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = day.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

// Event count indicator for dashboard
@Composable
fun EventCountCard(
    totalEvents: Int,
    upcomingEvents: Int,
    modifier: Modifier = Modifier
) {
    SmartFlowCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Events Today",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = totalEvents.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = SmartFlowButtonBlue
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Upcoming",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = upcomingEvents.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = SmartFlowTeal
                )
            }
        }
    }
}