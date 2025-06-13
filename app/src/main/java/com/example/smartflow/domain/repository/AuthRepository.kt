// domain/repository/AuthRepository.kt
package com.example.smartflow.domain.repository

import com.example.smartflow.data.remote.api.HealthResponse
import com.example.smartflow.data.remote.api.LoginResponse
import com.example.smartflow.data.remote.api.UserResponse
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
    suspend fun register(email: String, name: String, password: String): Resource<LoginResponse>
    suspend fun getProfile(): Resource<UserResponse>
    suspend fun logout(): Resource<Unit>
    fun isLoggedInFlow(): Flow<Boolean>
    suspend fun healthCheck(): Resource<HealthResponse>
    suspend fun createTestUser(): Resource<LoginResponse>
}
