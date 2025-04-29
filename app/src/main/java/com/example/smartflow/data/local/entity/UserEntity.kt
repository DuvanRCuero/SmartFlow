package com.example.smartflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime
import java.util.UUID

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val name: String?,
    val tz: String,
    val created_at: ZonedDateTime = ZonedDateTime.now(),
    // Adding password for local auth, not in the PostgreSQL schema
    val passwordHash: String? = null
)