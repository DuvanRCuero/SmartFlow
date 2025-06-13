package com.example.smartflow.domain.usecase


import com.example.smartflow.domain.model.User
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.util.Resource
import java.util.UUID
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Resource<User> {
        if (email.isBlank() || password.isBlank()) {
            return Resource.Error("Email and password cannot be empty")
        }

        return when (val apiResult = authRepository.login(email, password)) {
            is Resource.Success -> {
                val loginResponse = apiResult.data
                val user = User(
                    id    = UUID.fromString(loginResponse.user.id),
                    name  = loginResponse.user.name,
                    email = loginResponse.user.email,
                    timeZone = loginResponse.user.timeZone,
                    createdAt = loginResponse.user.createdAt
                )
                Resource.Success(user)
            }
            is Resource.Error   -> {
                Resource.Error(apiResult.message)
            }
            Resource.Loading -> {
                Resource.Loading
            }
        }
    }
}