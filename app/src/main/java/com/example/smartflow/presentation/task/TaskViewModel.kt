package com.example.smartflow.presentation.task

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Task(
    val id: String,
    val title: String,
    val subtitle: String
)

class TaskViewModel : ViewModel() {
    private val _ui = MutableStateFlow(TaskUiState())
    val ui: StateFlow<TaskUiState> = _ui
}
