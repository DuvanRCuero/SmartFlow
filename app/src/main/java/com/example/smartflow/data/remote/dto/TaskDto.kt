package com.example.smartflow.data.remote.dto

data class TaskDto(
    val id: String,
    val user_id: String,
    val title: String,
    val description: String? = null,
    val due_at: String? = null,
    val est_minutes: Int? = null,
    val energy_req: String? = null,
    val priority: Int? = null,
    val state: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val completed_at: String? = null
)
