package com.example.smartflow.data.remote.dto

data class UpdateTaskDto(
    val title: String? = null,
    val description: String? = null,
    val due_at: String? = null,
    val est_minutes: Int? = null,
    val energy_req: String? = null,
    val priority: Int? = null,
    val state: String? = null
)
