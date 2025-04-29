package com.example.smartflow.domain.model

import java.time.ZonedDateTime
import java.util.UUID

enum class TaskState {
    PENDING,
    IN_PROGRESS,
    DONE,
    ARCHIVED
}

enum class EnergyLevel {
    VERY_LOW,
    LOW,
    MEDIUM,
    HIGH,
    PEAK
}

data class Task(
    val id: UUID,
    val projectId: UUID?,
    val userId: UUID,
    val title: String,
    val description: String?,
    val dueAt: ZonedDateTime?,
    val estMinutes: Int?,
    val energyReq: EnergyLevel?,
    val priority: Int?,
    val state: TaskState,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime?,
    val completedAt: ZonedDateTime?
)