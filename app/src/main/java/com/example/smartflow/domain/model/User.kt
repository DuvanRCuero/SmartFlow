package com.example.smartflow.domain.model

import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    val timeZone: String,
    val createdAt: String
)