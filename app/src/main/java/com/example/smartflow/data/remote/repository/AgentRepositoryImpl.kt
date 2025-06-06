package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.AgentApi
import com.example.smartflow.data.remote.dto.AgentRequest
import com.example.smartflow.data.remote.dto.AgentResponse
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.util.Resource
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
            val userId = prefs.getUserId()
            val req = AgentRequest(
                user_id = userId,
                task_id = taskId,
                query   = query
            )
            val resp = api.runAgent(req)          // <-- llamada Retrofit
            Resource.Success(resp)                // envolver en Success
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }
}
