package com.example.smartflow.data.remote.dto

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String?,
    val tz: String
)