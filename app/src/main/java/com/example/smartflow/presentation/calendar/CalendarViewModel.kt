package com.example.smartflow.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import java.util.*

class CalendarViewModel : ViewModel() {

    private val _ui = MutableStateFlow(
        CalendarUiState(
            selectedDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            events = getSampleEvents()
        )
    )
    val ui: StateFlow<CalendarUiState> = _ui.asStateFlow()

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
        (dayOfWeek - 1).toFloat() / 6f
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0f
    )

    val dayEvents: StateFlow<List<Event>> = _ui.map { state ->
        state.events.filter { event ->
            state.selectedDay in 15..17
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onDaySelected(day: Int) {
        _ui.value = _ui.value.copy(selectedDay = day)
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
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    private fun getSampleEvents(): List<Event> {
        return listOf(
            Event(
                id = "1",
                title = "Project Meeting",
                time = "1:30 – 2:30 PM",
                isAlert = false,
                description = "Discuss Q4 roadmap and deliverables"
            ),
            Event(
                id = "2",
                title = "Sprint 21 Review",
                time = "11:30 AM – 12:30 PM",
                isAlert = true,
                description = "Review sprint accomplishments and blockers"
            ),
            Event(
                id = "3",
                title = "Design System Workshop",
                time = "3:00 – 4:30 PM",
                isAlert = false,
                description = "Collaborative design system improvements"
            ),
            Event(
                id = "4",
                title = "Client Call - SmartFlow Demo",
                time = "10:00 – 11:00 AM",
                isAlert = true,
                description = "Present new features to key stakeholders"
            )
        )
    }

    fun addEvent(event: Event) {
        val currentEvents = _ui.value.events.toMutableList()
        currentEvents.add(event)
        _ui.value = _ui.value.copy(events = currentEvents)
    }

    fun removeEvent(eventId: String) {
        val currentEvents = _ui.value.events.filterNot { it.id == eventId }
        _ui.value = _ui.value.copy(events = currentEvents)
    }

    fun toggleEventAlert(eventId: String) {
        val currentEvents = _ui.value.events.map { event ->
            if (event.id == eventId) {
                event.copy(isAlert = !event.isAlert)
            } else {
                event
            }
        }
        _ui.value = _ui.value.copy(events = currentEvents)
    }
}