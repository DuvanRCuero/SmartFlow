package com.example.smartflow.presentation.task

// Data classes
data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class Task(
    val id: String,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: String? = null,
    val timeAgo: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class TaskPriority {
    HIGH, MEDIUM, LOW
}