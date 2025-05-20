package com.example.smartflow.presentation.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartflow.presentation.common.ScreenScaffold

@Composable
fun CalendarScreen(
    onBackClick: () -> Unit = {},
    calendarViewModel: CalendarViewModel = viewModel()
) {
    val events by calendarViewModel.events.collectAsState()
    val monthProgress by calendarViewModel.monthProgress.collectAsState()
    val weekProgress by calendarViewModel.weekProgress.collectAsState()
    val selectedDay by calendarViewModel.selectedDay.collectAsState()

    ScreenScaffold(
        title = "Calendar",
        onBackClick = onBackClick
    ) {
        // Calendar view
        CalendarView(
            monthProgress = monthProgress,
            weekProgress = weekProgress,
            selectedDay = selectedDay,
            onDaySelected = { calendarViewModel.selectDay(it) }
        )

        // Events list
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            events.forEach { event ->
                EventItem(
                    title = event.title,
                    time = event.time,
                    isAlert = event.isAlert
                )
            }
        }
    }
}