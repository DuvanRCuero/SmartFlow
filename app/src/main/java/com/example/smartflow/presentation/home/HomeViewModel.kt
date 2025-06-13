package com.example.smartflow.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _ui = MutableStateFlow(HomeUiState())
    val ui: StateFlow<HomeUiState> = _ui.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        // In a real app, this would fetch data from repositories
        _ui.value = _ui.value.copy(
            userName = "Duvs",
            completedTasks = 1,
            pendingTasks = 0,
            recentTasks = getSampleTasks(),
            isLoading = false
        )
    }

    fun refreshDashboard() {
        _ui.value = _ui.value.copy(isLoading = true)
        loadDashboardData()
    }

    private fun getSampleTasks(): List<Task> {
        // Return empty list to show the empty state as per mockup
        return emptyList()

        // Uncomment below for sample tasks:
        /*
        return listOf(
            Task(
                id = "1",
                title = "Review project proposal",
                description = "Check the latest updates from the design team",
                isCompleted = true,
                timeAgo = "2h ago"
            ),
            Task(
                id = "2",
                title = "Schedule team meeting",
                description = "Arrange the weekly sync with the development team",
                isCompleted = false,
                timeAgo = "1h ago"
            ),
            Task(
                id = "3",
                title = "Update documentation",
                description = "Add new features to the user guide",
                isCompleted = true,
                timeAgo = "4h ago"
            )
        )
        */
    }
}

// Data classes
data class HomeUiState(
    val userName: String = "User",
    val completedTasks: Int = 0,
    val pendingTasks: Int = 0,
    val recentTasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class Task(
    val id: String,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val timeAgo: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: String? = null
)

enum class TaskPriority {
    HIGH, MEDIUM, LOW
}