package com.example.smartflow.data.remote.dto

data class SuggestionDto(
    val id: String,
    val user_id: String,
    val task_id: String? = null,
    val suggestion_time: String,
    val message: String,
    val reason: Map<String, Any>? = null,
    val confidence: Double? = null
)
