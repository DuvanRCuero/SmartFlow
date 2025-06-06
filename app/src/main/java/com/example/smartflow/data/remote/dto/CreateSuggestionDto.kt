package com.example.smartflow.data.remote.dto

data class CreateSuggestionDto(
    val user_id: String,
    val task_id: String?,
    val message: String,
    val reason: Map<String, Any>?,
    val confidence: Double?
)
