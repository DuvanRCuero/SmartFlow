package com.example.smartflow.data.remote.dto

data class AgentRequest(
    val user_id: String,
    val task_id: String? = null,
    val query: String? = null
)
