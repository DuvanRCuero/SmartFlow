package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.TaskApi
import com.example.smartflow.data.remote.api.TaskResponse
import com.example.smartflow.data.remote.api.CreateTaskRequest
import com.example.smartflow.data.remote.api.UpdateTaskRequest
import com.example.smartflow.data.remote.api.ChatRequest
import com.example.smartflow.data.remote.api.ChatResponse
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi,
    private val prefs: AuthPreferences
) : TaskRepository {

    override suspend fun getTasks(): Resource<List<TaskResponse>> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getTasks()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get tasks: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun getTask(id: String): Resource<TaskResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getTask(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get task: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun createTask(request: CreateTaskRequest): Resource<TaskResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.createTask(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to create task: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun updateTask(id: String, request: UpdateTaskRequest): Resource<TaskResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.updateTask(id, request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to update task: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun deleteTask(id: String): Resource<Unit> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.deleteTask(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Failed to delete task: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun sendMessage(message: String): Resource<ChatResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val request = ChatRequest(message = message)
            val response = api.sendMessage(request)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to send message: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}