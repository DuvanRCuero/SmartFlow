package com.example.smartflow.presentation.task

data class Insight(val id: String, val title: String, val subtitle: String)

data class TaskUiState(
    val completed: Int = 64,
    val deltaPercent: Float = 2f,
    val insights: List<Insight> = listOf(
        Insight("1", "Schedule deep work at 10 AM", "your focus peak"),
        Insight("2", "Focus on meetings after lunch", "your not productive time")
    )
)
