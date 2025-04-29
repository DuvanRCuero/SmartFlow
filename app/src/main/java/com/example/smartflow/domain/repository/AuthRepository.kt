package com.example.smartflow.domain.repository

import com.example.smartflow.domain.model.User
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    /**
     * Attempt to login user with email and password
     */
    suspend fun login(email: String, password: String): Resource<User>

    /**
     * Register a new user
     */
    suspend fun signup(email: String, password: String, name: String?, timeZone: String): Resource<User>

    /**
     * Get current logged in user if any
     */
    fun getCurrentUser(): Flow<User?>

    /**
     * Check if a user is currently logged in
     */
    fun isLoggedIn(): Flow<Boolean>

    /**
     * Logout the current user
     */
    suspend fun logout()

    /** Register a new user account */
    suspend fun register(email: String, password: String, name: String): Result<Unit>
}