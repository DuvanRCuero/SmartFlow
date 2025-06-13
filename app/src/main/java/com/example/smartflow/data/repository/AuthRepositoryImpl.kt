// data/repository/AuthRepositoryImpl.kt
package com.example.smartflow.data.repository

import com.example.smartflow.data.local.dao.UserDao
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.data.remote.api.AuthApi
import com.example.smartflow.data.remote.api.HealthResponse
import com.example.smartflow.data.remote.api.LoginRequest
import com.example.smartflow.data.remote.api.LoginResponse
import com.example.smartflow.data.remote.api.RegisterRequest
import com.example.smartflow.data.remote.api.TestUserResponse
import com.example.smartflow.data.remote.api.UserResponse
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
                prefs.saveCompleteAuth(
                    token  = loginResponse.token,
                    userId = loginResponse.user.id,
                    email  = loginResponse.user.email,
                    name   = loginResponse.user.name
                )
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
                prefs.saveCompleteAuth(
                    token  = loginResponse.token,
                    userId = loginResponse.user.id,
                    email  = loginResponse.user.email,
                    name   = loginResponse.user.name
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
            prefs.clearAuth()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to logout")
        }
    }

    override fun isLoggedInFlow(): Flow<Boolean> =
        flow {
            emit(prefs.isLoggedIn())
            // If your prefs can notify on changes you could collect that here
        }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun healthCheck(): Resource<HealthResponse> {
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

    override suspend fun createTestUser(): Resource<LoginResponse> {
        return try {
            val response = api.createTestUser()
            if (response.isSuccessful && response.body() != null) {
                val testResponse = response.body()!!
                val loginResponse = testResponse.data ?: return Resource.Error("Test user creation failed")
                prefs.saveCompleteAuth(
                    token  = loginResponse.token,
                    userId = loginResponse.user.id,
                    email  = loginResponse.user.email,
                    name   = loginResponse.user.name
                )
                Resource.Success(loginResponse)
            } else {
                Resource.Error("Test user creation failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}
