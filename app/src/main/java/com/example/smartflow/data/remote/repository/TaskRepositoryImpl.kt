// Pattern 1: TaskRepositoryImpl.kt
package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.TaskApi
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
            // Check authentication
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

// Pattern 2: LogRepositoryImpl.kt
@Singleton
class LogRepositoryImpl @Inject constructor(
    private val api: LogApi,
    private val prefs: AuthPreferences
) : LogRepository {

    override suspend fun getLogs(): Resource<List<LogResponse>> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getLogs()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get logs: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}

// Pattern 3: SuggestionRepositoryImpl.kt
@Singleton
class SuggestionRepositoryImpl @Inject constructor(
    private val api: SuggestionApi,
    private val prefs: AuthPreferences
) : SuggestionRepository {

    override suspend fun getSuggestions(): Resource<List<SuggestionResponse>> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getSuggestions()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get suggestions: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}

// Data classes you might need (create these if they don't exist)
data class TaskResponse(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val created_at: String
)

data class ChatRequest(val message: String)
data class ChatResponse(
    val response: String,
    val user_id: String,
    val timestamp: String
)

data class LogResponse(
    val id: String,
    val message: String,
    val timestamp: String
)

data class SuggestionResponse(
    val id: String,
    val title: String,
    val description: String
)