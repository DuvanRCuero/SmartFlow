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
        EventItemRowWrapper(title, time, isAlert)
    }
}

@Composable
fun EventItemRowWrapper(
    title: String,
    time: String,
    isAlert: Boolean = false
) {
    EventItemRow(title, time, isAlert)
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
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(time, style = MaterialTheme.typography.bodyMedium, color = Gray)
        }

        if (!isAlert) {
            IconButton(
                onClick = { /* TODO: acción */ },
                modifier = Modifier
                    .size(32.dp)
                    .background(SmartFlowTeal, CircleShape)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = White, modifier = Modifier.size(16.dp))
            }
        } else {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(SmartFlowButtonBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("!", color = White)
            }
        }
    }
}