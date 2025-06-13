package com.example.smartflow.presentation.task

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class TaskViewModel : ViewModel() {
    private val _ui = MutableStateFlow(TasksUiState())
    val ui: StateFlow<TasksUiState> = _ui.asStateFlow()

    fun addNewTask(
        title: String = "New Task",
        description: String = "Task description"
    ) {
        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            isCompleted = false
        )

        val currentTasks = _ui.value.tasks.toMutableList()
        currentTasks.add(newTask)
        _ui.value = _ui.value.copy(tasks = currentTasks)
    }

    fun toggleTaskComplete(taskId: String) {
        val updatedTasks = _ui.value.tasks.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        }
        _ui.value = _ui.value.copy(tasks = updatedTasks)
    }

    fun deleteTask(taskId: String) {
        val updatedTasks = _ui.value.tasks.filter { it.id != taskId }
        _ui.value = _ui.value.copy(tasks = updatedTasks)
    }

    fun updateTask(taskId: String, title: String, description: String) {
        val updatedTasks = _ui.value.tasks.map { task ->
            if (task.id == taskId) {
                task.copy(title = title, description = description)
            } else {
                task
            }
        }
        _ui.value = _ui.value.copy(tasks = updatedTasks)
    }
}
