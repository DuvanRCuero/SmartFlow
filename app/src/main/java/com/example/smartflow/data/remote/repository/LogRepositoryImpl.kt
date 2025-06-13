package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.LogApi
import com.example.smartflow.data.remote.api.LogResponse
import com.example.smartflow.data.remote.api.CreateLogRequest
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogRepositoryImpl @Inject constructor(
    private val api: LogApi,
    private val prefs: AuthPreferences
) : LogRepository {

    override suspend fun getLogs(): Resource<List<LogResponse>> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getLogs()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get logs: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun createLog(request: CreateLogRequest): Resource<LogResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.createLog(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to create log: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun getLog(id: String): Resource<LogResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getLog(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get log: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun deleteLog(id: String): Resource<Unit> {
        TODO("Not yet implemented")
    }
}

