package com.example.smartflow.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import com.example.smartflow.presentation.common.SfBottomBar
import com.example.smartflow.presentation.navigation.SfDestination
import com.example.smartflow.ui.theme.SmartFlowButtonBlue
import com.example.smartflow.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onBack: () -> Unit,
    vm: CalendarViewModel = viewModel()
) {
    val uiState by vm.ui.collectAsState()
    val monthProgress by vm.monthProgress.collectAsState()
    val weekProgress by vm.weekProgress.collectAsState()
    val events by vm.dayEvents.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Calendar",
                        color = White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }
                }
            )
        },
        bottomBar = { SfBottomBar(selected = SfDestination.Calendar) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Month and Year Header
            MonthYearHeader(
                month = vm.getCurrentMonth(),
                year = vm.getCurrentYear(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Calendar Grid Section
            CalendarView(
                monthProgress = monthProgress,
                weekProgress = weekProgress,
                selectedDay = uiState.selectedDay,
                onDaySelected = vm::onDaySelected,
                firstDayOfWeek = vm.getFirstDayOfMonth(),
                daysInMonth = vm.getDaysInCurrentMonth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Events Section Header
            Text(
                text = "Today's Events",
                style = MaterialTheme.typography.titleMedium,
                color = White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Events List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(events) { event ->
                    EventItem(
                        title = event.title,
                        time = event.time,
                        isAlert = event.isAlert
                    )
                }

                if (events.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No events for this day",
                                color = White.copy(alpha = 0.6f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthYearHeader(
    month: String,
    year: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$month $year",
            style = MaterialTheme.typography.headlineSmall,
            color = White,
            fontWeight = FontWeight.Bold
        )

        // You could add navigation arrows here for month switching
    }
}

@Composable
fun CalendarView(
    monthProgress: Float,
    weekProgress: Float,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    firstDayOfWeek: Int,
    daysInMonth: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LinearProgressIndicator(
            progress = { monthProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = SmartFlowButtonBlue,
            trackColor = White.copy(alpha = 0.3f)
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
                    color = White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            state = rememberLazyGridState()
        ) {
            // Empty cells for days before the first day of month
            val offset = firstDayOfWeek
            items(offset) {
                Box(Modifier.size(40.dp))
            }

            // Days of the month
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
                    Text(
                        text = day.toString(),
                        color = White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { weekProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = SmartFlowButtonBlue,
            trackColor = White.copy(alpha = 0.3f)
        )
    }
}