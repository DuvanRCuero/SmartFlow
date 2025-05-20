package com.example.smartflow.presentation.calendar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CalendarEvent(
    val id: String,
    val title: String,
    val time: String,
    val isAlert: Boolean = false
)

class CalendarViewModel : ViewModel() {
    private val _events = MutableStateFlow<List<CalendarEvent>>(
        listOf(
            CalendarEvent(
                id = "1",
                title = "Project Meeting",
                time = "10:00 AM"
            ),
            CalendarEvent(
                id = "2",
                title = "Sprint 21 Review",
                time = "2:00 PM",
                isAlert = true
            )
        )
    )
    val events: StateFlow<List<CalendarEvent>> = _events.asStateFlow()

    // Month progress (0.0 to 1.0)
    private val _monthProgress = MutableStateFlow(0.6f)
    val monthProgress: StateFlow<Float> = _monthProgress.asStateFlow()

    // Week progress (0.0 to 1.0)
    private val _weekProgress = MutableStateFlow(0.4f)
    val weekProgress: StateFlow<Float> = _weekProgress.asStateFlow()

    // Selected day in calendar
    private val _selectedDay = MutableStateFlow(15)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }
}