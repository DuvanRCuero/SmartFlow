package com.example.smartflow.presentation.task

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Task(
    val id: String,
    val title: String,
    val subtitle: String
)

class TaskViewModel : ViewModel() {
    private val _tasksCompleted = MutableStateFlow(64)
    val tasksCompleted: StateFlow<Int> = _tasksCompleted.asStateFlow()

    private val _tasksCompletedChangePercent = MutableStateFlow("+2%")
    val tasksCompletedChangePercent: StateFlow<String> = _tasksCompletedChangePercent.asStateFlow()

    private val _weeklyTasksData = MutableStateFlow(listOf(12, 15, 10, 9, 18))
    val weeklyTasksData: StateFlow<List<Int>> = _weeklyTasksData.asStateFlow()

    private val _suggestedTasks = MutableStateFlow(
        listOf(
            Task(
                id = "1",
                title = "Schedule deep work at 10 AM",
                subtitle = "your focus peak"
            ),
            Task(
                id = "2",
                title = "Focus on meetings after lunch",
                subtitle = "your non-productive time"
            )
        )
    )
    val suggestedTasks: StateFlow<List<Task>> = _suggestedTasks.asStateFlow()
}