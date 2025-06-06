package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.AgentApi
import com.example.smartflow.data.remote.dto.AgentRequest
import com.example.smartflow.data.remote.dto.AgentResponse
import com.example.smartflow.data.local.preferences.AuthPreferences

class AgentRepositoryImpl(
    private val api: AgentApi,
    private val prefs: AuthPreferences
) : AgentRepository {

    override suspend fun runAgent(query: String, taskId: String?): AgentResponse {
        val userId = prefs.getUserId()
        val req = AgentRequest(
            user_id = userId,
            task_id = taskId,
            query = query
        )
        return api.runAgent(req)
    }
}
