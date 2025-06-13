package com.example.smartflow.data.remote.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*


interface AgentApi {
    @POST("agent/run")
    suspend fun runAgent(@Body request: AgentRequest): Response<AgentResponse>

    @GET("agent/status/{taskId}")
    suspend fun getAgentStatus(@Path("taskId") taskId: String): Response<AgentStatusResponse>

    @POST("agent/stop/{taskId}")
    suspend fun stopAgent(@Path("taskId") taskId: String): Response<Unit>

    @GET("agent/history")
    suspend fun getAgentHistory(): Response<List<AgentResponse>>

    @GET("agent/{id}")
    suspend fun getAgent(@Path("id") id: String): Response<AgentResponse>
}

@Serializable
data class AgentRequest(
    val user_id: String,
    val task_id: String? = null,
    val query: String,
    val agent_type: String = "default"
)

@Serializable
data class AgentResponse(
    val id: String,
    val response: String,
    val status: String, // running, completed, failed
    val task_id: String? = null,
    val user_id: String? = null,
    val created_at: String,
    val completed_at: String? = null,
    val metadata: Map<String, String>? = null
)

@Serializable
data class AgentStatusResponse(
    val id: String,
    val status: String,
    val progress: Int = 0, // 0-100
    val message: String? = null,
    val estimated_completion: String? = null
)