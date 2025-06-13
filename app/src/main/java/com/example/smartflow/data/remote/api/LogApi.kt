package com.example.smartflow.data.remote.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface LogApi {
    @GET("logs")
    suspend fun getLogs(): Response<List<LogResponse>>

    @POST("logs")
    suspend fun createLog(@Body request: CreateLogRequest): Response<LogResponse>

    @GET("logs/{id}")
    suspend fun getLog(@Path("id") id: String): Response<LogResponse>

    @DELETE("logs/{id}")
    suspend fun deleteLog(@Path("id") id: String): Response<Unit>

    @GET("logs/user/{userId}")
    suspend fun getUserLogs(@Path("userId") userId: String): Response<List<LogResponse>>
}

@Serializable
data class LogResponse(
    val id: String,
    val message: String,
    val level: String = "info", // info, warning, error
    val timestamp: String,
    val user_id: String? = null,
    val task_id: String? = null,
    val category: String? = null
)

@Serializable
data class CreateLogRequest(
    val message: String,
    val level: String = "info",
    val task_id: String? = null,
    val category: String? = null
)


