package com.example.smartflow.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
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
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.navigation.SfDestination
import com.example.smartflow.ui.theme.SmartFlowButtonBlue
import com.example.smartflow.ui.theme.SmartFlowTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onBack: () -> Unit,
    onSelectBottom: (SfDestination) -> Unit = {},
    vm: CalendarViewModel = viewModel()
) {
    val uiState by vm.ui.collectAsState()
    val monthProgress by vm.monthProgress.collectAsState()
    val weekProgress by vm.weekProgress.collectAsState()
    val events by vm.dayEvents.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Calendar",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            SfBottomBar(
                selected = SfDestination.Calendar,
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
            // Month/Year Header with navigation
            item {
                MonthYearHeader(
                    month = vm.getCurrentMonth(),
                    year = vm.getCurrentYear(),
                    onPreviousMonth = { /* TODO: Implement month navigation */ },
                    onNextMonth = { /* TODO: Implement month navigation */ }
                )
            }

            // Progress Indicators Card
            item {
                ProgressCard(
                    monthProgress = monthProgress,
                    weekProgress = weekProgress
                )
            }

            // Calendar Grid
            item {
                CalendarCard(
                    selectedDay = uiState.selectedDay,
                    onDaySelected = vm::onDaySelected,
                    firstDayOfWeek = vm.getFirstDayOfMonth(),
                    daysInMonth = vm.getDaysInCurrentMonth()
                )
            }

            // Events Section Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Events",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    IconButton(
                        onClick = { /* TODO: Add new event */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(SmartFlowTeal, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Event",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Events List
            if (events.isNotEmpty()) {
                items(events) { event ->
                    EnhancedEventItem(
                        title = event.title,
                        time = event.time,
                        isAlert = event.isAlert
                    )
                }
            } else {
                item {
                    EmptyEventsCard()
                }
            }
        }
    }
}

@Composable
private fun MonthYearHeader(
    month: String,
    year: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    SmartFlowCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(
                    Icons.Outlined.ChevronLeft,
                    contentDescription = "Previous Month",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "$month $year",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            IconButton(onClick = onNextMonth) {
                Icon(
                    Icons.Outlined.ChevronRight,
                    contentDescription = "Next Month",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun ProgressCard(
    monthProgress: Float,
    weekProgress: Float
) {
    SmartFlowCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Month Progress",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "${(monthProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = SmartFlowButtonBlue
                    )
                }

                LinearProgressIndicator(
                    progress = { monthProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = SmartFlowButtonBlue,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Week Progress",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "${(weekProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = SmartFlowTeal
                    )
                }

                LinearProgressIndicator(
                    progress = { weekProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = SmartFlowTeal,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CalendarCard(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    firstDayOfWeek: Int,
    daysInMonth: Int
) {
    SmartFlowCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Days of week header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // Calendar grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.height(240.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Empty cells for days before the first day of month
                items(firstDayOfWeek) {
                    Box(Modifier.size(40.dp))
                }

                // Days of the month
                items(daysInMonth) { index ->
                    val day = index + 1
                    val isSelected = day == selectedDay
                    val isToday = day == java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)

                    Surface(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onDaySelected(day) },
                        shape = CircleShape,
                        color = when {
                            isSelected -> SmartFlowButtonBlue
                            isToday -> SmartFlowTeal.copy(alpha = 0.2f)
                            else -> Color.Transparent
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                                color = when {
                                    isSelected -> Color.White
                                    isToday -> SmartFlowTeal
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EnhancedEventItem(
    title: String,
    time: String,
    isAlert: Boolean = false
) {
    SmartFlowCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Event indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
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

            if (isAlert) {
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
            } else {
                IconButton(
                    onClick = { /* TODO: Add to calendar */ },
                    modifier = Modifier
                        .size(32.dp)
                        .background(SmartFlowTeal, CircleShape)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add to Calendar",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyEventsCard() {
    SmartFlowCard {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📅",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "No events today",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Tap the + button to add an event",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}