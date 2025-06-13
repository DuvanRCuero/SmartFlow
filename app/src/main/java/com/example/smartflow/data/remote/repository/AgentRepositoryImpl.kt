package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.AgentApi
import com.example.smartflow.data.remote.api.AgentRequest
import com.example.smartflow.data.remote.api.AgentResponse
import com.example.smartflow.data.remote.api.AgentStatusResponse
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentRepositoryImpl @Inject constructor(
    private val api: AgentApi,
    private val prefs: AuthPreferences
) : AgentRepository {

    override suspend fun runAgent(query: String, taskId: String?): Resource<AgentResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val request = AgentRequest(
                user_id = userId,
                task_id = taskId,
                query = query
            )

            val response = api.runAgent(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to run agent: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun getAgentStatus(taskId: String): Resource<AgentStatusResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getAgentStatus(taskId)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get agent status: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun stopAgent(taskId: String): Resource<Unit> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.stopAgent(taskId)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Failed to stop agent: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun getAgentHistory(): Resource<List<AgentResponse>> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getAgentHistory()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get agent history: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}