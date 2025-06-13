package com.example.smartflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartflow.data.remote.api.UserResponse

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLogin: Long? = null
)

// Extension functions for conversion
fun UserEntity.toUserResponse(): UserResponse {
    return UserResponse(
        id = this.id,
        email = this.email,
        name = this.name,
        timeZone = "UTC", // Ajusta según la lógica de tu aplicación
        createdAt = this.createdAt.toString()
    )
}

fun UserResponse.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        name = this.name,
        createdAt = this.createdAt.toLong()
    )
}