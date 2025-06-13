// data/remote/api/AuthApi.kt
package com.example.smartflow.data.remote.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("auth/me")
    suspend fun getProfile(): Response<UserResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>

    @GET("health")
    suspend fun healthCheck(): Response<HealthResponse>

    @POST("test/create-user")
    suspend fun createTestUser(): Response<TestUserResponse>
}
@Serializable
data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val name: String,
    val timeZone: String,
    val createdAt: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val user: UserResponse
)

@Serializable
data class HealthResponse(
    val status: String,
    val platform: String? = null,
    val database: String? = null,
    val timestamp: String? = null,
    val mode: String? = null
)

@Serializable
data class TestUserResponse(
    val success: Boolean,
    val message: String,
    val platform: String? = null,
    val data: LoginResponse? = null,
    val instructions: List<String>? = null
)