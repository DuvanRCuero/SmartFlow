// data/remote/api/TaskApi.kt
package com.example.smartflow.data.remote.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {
    @GET("tasks")
    suspend fun getTasks(): Response<List<TaskResponse>>

    @POST("tasks")
    suspend fun createTask(@Body request: CreateTaskRequest): Response<TaskResponse>

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Body request: UpdateTaskRequest
    ): Response<TaskResponse>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String): Response<Unit>

    @GET("tasks/{id}")
    suspend fun getTask(@Path("id") id: String): Response<TaskResponse>

    @POST("chat/windows")
    suspend fun sendMessage(@Body request: ChatRequest): Response<ChatResponse>
}

@Serializable
data class TaskResponse(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val created_at: String,
    val updated_at: String? = null,
    val user_id: String? = null
)

@Serializable
data class CreateTaskRequest(
    val title: String,
    val description: String,
    val priority: String = "medium"
)

@Serializable
data class UpdateTaskRequest(
    val title: String? = null,
    val description: String? = null,
    val status: String? = null,
    val priority: String? = null
)

@Serializable
data class ChatRequest(
    val message: String
)

@Serializable
data class ChatResponse(
    val response: String,
    val user_id: String,
    val timestamp: String,
    val platform: String? = null,
    val mode: String? = null
)