package com.example.smartflow.data.remote.api

import com.example.smartflow.data.remote.dto.AuthRequest
import com.example.smartflow.data.remote.dto.AuthResponse
import com.example.smartflow.data.remote.dto.SignupRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse
}