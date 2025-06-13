package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.LogResponse
import com.example.smartflow.data.remote.api.CreateLogRequest
import com.example.smartflow.util.Resource

interface LogRepository {
    suspend fun getLogs(): Resource<List<LogResponse>>
    suspend fun createLog(request: CreateLogRequest): Resource<LogResponse>
    suspend fun getLog(id: String): Resource<LogResponse>
    suspend fun deleteLog(id: String): Resource<Unit>
}
