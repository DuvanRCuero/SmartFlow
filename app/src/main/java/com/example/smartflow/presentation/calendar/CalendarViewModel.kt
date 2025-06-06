package com.example.smartflow.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import java.util.*

class CalendarViewModel : ViewModel() {

    private val _ui = MutableStateFlow(
        CalendarUiState(
            selectedDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            events = listOf(
                Event("1", "Project Meeting", "1:30 – 2:30 PM", false),
                Event("2", "Sprint 21 Review", "11:30 – 12:30 AM", true)
            )
        )
    )
    val ui: StateFlow<CalendarUiState> = _ui

    // Progress indicators
    val monthProgress: StateFlow<Float> = _ui.map { state ->
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        currentDay.toFloat() / daysInMonth.toFloat()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0f
    )

    val weekProgress: StateFlow<Float> = _ui.map { state ->
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        (dayOfWeek - 1).toFloat() / 6f // Sunday = 1, so we subtract 1
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0f
    )

    val dayEvents: StateFlow<List<Event>> = _ui.map { state ->
        // Filter events for selected day (simplified - in real app you'd filter by actual date)
        state.events
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onDaySelected(day: Int) {
        _ui.value = _ui.value.copy(selectedDay = day)
        // TODO: Load real events for the selected day
        // In a real implementation, you would fetch events from repository
    }

    fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val monthNames = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return monthNames[calendar.get(Calendar.MONTH)]
    }

    fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun getDaysInCurrentMonth(): Int {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getFirstDayOfMonth(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.get(Calendar.DAY_OF_WEEK) - 1 // Convert to 0-based index
    }
}