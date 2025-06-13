// Create this file: data/remote/repository/AgentRepository.kt
package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.AgentResponse
import com.example.smartflow.data.remote.api.AgentStatusResponse
import com.example.smartflow.util.Resource

interface AgentRepository {
    suspend fun runAgent(query: String, taskId: String? = null): Resource<AgentResponse>
    suspend fun getAgentStatus(taskId: String): Resource<AgentStatusResponse>
    suspend fun stopAgent(taskId: String): Resource<Unit>
    suspend fun getAgentHistory(): Resource<List<AgentResponse>>
}