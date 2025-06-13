package com.example.smartflow.data.repository

import com.example.smartflow.data.remote.api.AuthApi
import com.example.smartflow.data.local.dao.UserDao
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val userDao: UserDao,
    private val prefs: AuthPreferences
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return try {
            val request = LoginRequest(email = email, password = password)
            val response = api.login(request)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Save auth data
                prefs.saveCompleteAuth(
                    token = loginResponse.token,
                    userId = loginResponse.user.id,
                    email = loginResponse.user.email,
                    name = loginResponse.user.name
                )

                // Optionally save to Room database
                // userDao.insertUser(loginResponse.user.toEntity())

                Resource.Success(loginResponse)
            } else {
                Resource.Error("Login failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun register(email: String, name: String, password: String): Resource<LoginResponse> {
        return try {
            val request = RegisterRequest(email = email, name = name, password = password)
            val response = api.register(request)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Save auth data
                prefs.saveCompleteAuth(
                    token = loginResponse.token,
                    userId = loginResponse.user.id,
                    email = loginResponse.user.email,
                    name = loginResponse.user.name
                )

                Resource.Success(loginResponse)
            } else {
                Resource.Error("Registration failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun getProfile(): Resource<UserResponse> {
        return try {
            // Check if user is authenticated
            if (!prefs.isLoggedIn()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getProfile()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get profile: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            // Clear local data
            prefs.clearAuth()
            // Optionally clear Room database
            // userDao.clearAll()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to logout")
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return prefs.isLoggedIn()
    }

    // Health check method for testing
    suspend fun healthCheck(): Resource<HealthResponse> {
        return try {
            val response = api.healthCheck()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Health check failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    // Test user creation for development
    suspend fun createTestUser(): Resource<LoginResponse> {
        return try {
            val response = api.createTestUser()

            if (response.isSuccessful && response.body() != null) {
                val testResponse = response.body()!!
                if (testResponse.data != null) {
                    val loginResponse = testResponse.data

                    // Save auth data
                    prefs.saveCompleteAuth(
                        token = loginResponse.token,
                        userId = loginResponse.user.id,
                        email = loginResponse.user.email,
                        name = loginResponse.user.name
                    )

                    Resource.Success(loginResponse)
                } else {
                    Resource.Error("Test user creation failed")
                }
            } else {
                Resource.Error("Test user creation failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}

// Data classes (if not already defined)
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val email: String, val name: String, val password: String)
data class UserResponse(val id: String, val email: String, val name: String)
data class LoginResponse(val token: String, val user: UserResponse)
data class HealthResponse(val status: String, val platform: String?, val database: String?)
data class TestUserResponse(val success: Boolean, val message: String, val data: LoginResponse?)