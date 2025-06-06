package com.example.smartflow.data.remote.api

import com.example.smartflow.data.remote.dto.AgentRequest
import com.example.smartflow.data.remote.dto.AgentResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AgentApi {
    @POST("agent/run")
    suspend fun runAgent(@Body body: AgentRequest): AgentResponse
}
