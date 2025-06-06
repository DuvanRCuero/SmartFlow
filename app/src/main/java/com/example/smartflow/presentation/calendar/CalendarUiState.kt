package com.example.smartflow.presentation.calendar

data class CalendarUiState(
    val selectedDay: Int = 1,
    val events: List<Event> = emptyList()
)

data class Event(
    val id: String,
    val title: String,
    val time: String,
    val isAlert: Boolean = false
)