package com.example.smartflow.data.remote.dto

data class CreateLogDto(
    val user_id: String,
    val focus_score: Double,
    val energy_level: String,
    val context: Map<String, Any>? = null
)
