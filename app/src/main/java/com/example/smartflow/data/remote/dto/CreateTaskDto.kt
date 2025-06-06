package com.example.smartflow.data.remote.dto

data class CreateTaskDto(
    val user_id: String,
    val title: String,
    val description: String?,
    val due_at: String?,
    val est_minutes: Int?,
    val energy_req: String?,
    val priority: Int?
)
