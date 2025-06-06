package com.example.smartflow.data.remote.api

import com.example.smartflow.data.remote.dto.TaskDto
import com.example.smartflow.data.remote.dto.CreateTaskDto
import com.example.smartflow.data.remote.dto.UpdateTaskDto
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {

    @GET("tasks")
    suspend fun getTasks(@Query("user_id") userId: String): List<TaskDto>

    @POST("tasks")
    suspend fun createTask(@Body body: CreateTaskDto): TaskDto

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") taskId: String,
        @Body body: UpdateTaskDto
    ): TaskDto

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") taskId: String): Response<Unit>
}
