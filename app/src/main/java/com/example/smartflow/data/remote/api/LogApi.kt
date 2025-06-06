package com.example.smartflow.data.remote.api

import com.example.smartflow.data.remote.dto.LogDto
import com.example.smartflow.data.remote.dto.CreateLogDto
import retrofit2.http.*

interface LogApi {

    @GET("logs")
    suspend fun getLogs(@Query("user_id") userId: String): List<LogDto>

    @POST("logs")
    suspend fun createLog(@Body body: CreateLogDto): LogDto
}
