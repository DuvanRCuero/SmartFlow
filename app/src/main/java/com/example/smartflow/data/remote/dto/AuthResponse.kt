package com.example.smartflow.data.remote.dto

data class AuthResponse(
    val userId: String,
    val email: String,
    val name: String?,
    val token: String?,
    val success: Boolean,
    val message: String?
)