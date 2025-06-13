package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.AgentApi
import com.example.smartflow.data.remote.dto.AgentRequest
import com.example.smartflow.data.remote.dto.AgentResponse
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

    override suspend fun runAgent(
        query: String,
        taskId: String?
    ): Resource<AgentResponse> {
        return try {
            // Get userId from Flow - use first() to get the current value
            val userId = prefs.userId.first()

            // Check if user is authenticated
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val req = AgentRequest(
                user_id = userId,
                task_id = taskId,
                query = query
            )

            val resp = api.runAgent(req)
            Resource.Success(resp)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}