package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.LogApi
import com.example.smartflow.data.remote.dto.CreateLogDto
import com.example.smartflow.data.remote.dto.LogDto
import com.example.smartflow.data.local.preferences.AuthPreferences

class LogRepositoryImpl(
    private val api: LogApi,
    private val prefs: AuthPreferences
) : LogRepository {

    override suspend fun getLogs(): List<LogDto> {
        val userId = prefs.getUserId()
        return api.getLogs(userId)
    }

    override suspend fun createLog(
        focusScore: Double,
        energyLevel: String,
        context: Map<String, Any>?
    ): LogDto {
        val userId = prefs.getUserId()
        val dto = CreateLogDto(
            user_id = userId,
            focus_score = focusScore,
            energy_level = energyLevel,
            context = context
        )
        return api.createLog(dto)
    }
}
