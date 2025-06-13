// data/remote/repository/TaskRepository.kt
package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.TaskResponse
import com.example.smartflow.data.remote.api.CreateTaskRequest
import com.example.smartflow.data.remote.api.UpdateTaskRequest
import com.example.smartflow.data.remote.api.ChatResponse
import com.example.smartflow.util.Resource

interface TaskRepository {
    suspend fun getTasks(): Resource<List<TaskResponse>>
    suspend fun getTask(id: String): Resource<TaskResponse>
    suspend fun createTask(request: CreateTaskRequest): Resource<TaskResponse>
    suspend fun updateTask(id: String, request: UpdateTaskRequest): Resource<TaskResponse>
    suspend fun deleteTask(id: String): Resource<Unit>
    suspend fun sendMessage(message: String): Resource<ChatResponse>
}