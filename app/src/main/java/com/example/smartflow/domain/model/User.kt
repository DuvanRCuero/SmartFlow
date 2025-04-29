package com.example.smartflow.domain.model

import java.time.ZonedDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val name: String?,
    val timeZone: String,
    val createdAt: ZonedDateTime
)