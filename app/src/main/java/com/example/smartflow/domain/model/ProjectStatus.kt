package com.example.smartflow.domain.model

import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

enum class ProjectStatus {
    DRAFT,
    ACTIVE,
    ARCHIVED
}

data class Project(
    val id: UUID,
    val userId: UUID,
    val name: String,
    val description: String?,
    val status: ProjectStatus,
    val startsAt: LocalDate?,
    val endsAt: LocalDate?,
    val createdAt: ZonedDateTime
)