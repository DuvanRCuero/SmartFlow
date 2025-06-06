package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.TaskApi
import com.example.smartflow.data.remote.dto.CreateTaskDto
import com.example.smartflow.data.remote.dto.TaskDto
import com.example.smartflow.data.remote.dto.UpdateTaskDto
import com.example.smartflow.data.local.preferences.AuthPreferences

class TaskRepositoryImpl(
    private val api: TaskApi,
    private val prefs: AuthPreferences
) : TaskRepository {

    override suspend fun getTasks(): List<TaskDto> {
        val userId = prefs.getUserId()  // suponiendo que devuelva String
        return api.getTasks(userId)
    }

    override suspend fun createTask(
        title: String,
        description: String?,
        dueAt: String?,
        estMinutes: Int?,
        energyReq: String?,
        priority: Int?
    ): TaskDto {
        val userId = prefs.getUserId()
        val dto = CreateTaskDto(
            user_id = userId,
            title = title,
            description = description,
            due_at = dueAt,
            est_minutes = estMinutes,
            energy_req = energyReq,
            priority = priority
        )
        return api.createTask(dto)
    }

    override suspend fun updateTask(
        taskId: String,
        title: String?,
        description: String?,
        dueAt: String?,
        estMinutes: Int?,
        energyReq: String?,
        priority: Int?,
        state: String?
    ): TaskDto {
        val dto = UpdateTaskDto(
            title = title,
            description = description,
            due_at = dueAt,
            est_minutes = estMinutes,
            energy_req = energyReq,
            priority = priority,
            state = state
        )
        return api.updateTask(taskId, dto)
    }

    override suspend fun deleteTask(taskId: String) {
        api.deleteTask(taskId)
    }
}
