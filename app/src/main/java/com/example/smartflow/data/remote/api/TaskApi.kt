// data/remote/api/TaskApi.kt
package com.example.smartflow.data.remote.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {
    @GET("tasks")
    suspend fun getTasks(): Response<List<TaskResponse>>

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
    val updated_at: String? = null
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