package com.example.smartflow.domain.usecase

import com.example.smartflow.domain.model.User
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.util.Resource
import timber.log.Timber
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        if (email.isBlank() || password.isBlank()) {
            return Resource.Error("Email and password cannot be empty")
        }

        return try {
            authRepository.login(email, password)
        } catch (e: Exception) {
            Timber.e(e, "Error during login")
            Resource.Error("Login failed: ${e.message}", e)
        }
    }
}