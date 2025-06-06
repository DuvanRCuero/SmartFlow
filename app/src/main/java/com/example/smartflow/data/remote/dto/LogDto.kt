package com.example.smartflow.data.remote.dto

data class LogDto(
    val id: String,
    val user_id: String,
    val ts: String,
    val focus_score: Double,
    val energy_level: String,
    val context: Map<String, Any>? = null
)
