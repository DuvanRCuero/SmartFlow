package com.example.smartflow.presentation.calendar

data class CalendarUiState(
    val selectedDay: Int = 1,
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class Event(
    val id: String,
    val title: String,
    val time: String,
    val isAlert: Boolean = false,
    val description: String? = null,
    val date: String? = null // In real app, use proper Date/LocalDateTime
)