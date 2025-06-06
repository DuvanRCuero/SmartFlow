package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.SuggestionApi
import com.example.smartflow.data.remote.dto.CreateSuggestionDto
import com.example.smartflow.data.remote.dto.SuggestionDto
import com.example.smartflow.data.local.preferences.AuthPreferences

class SuggestionRepositoryImpl(
    private val api: SuggestionApi,
    private val prefs: AuthPreferences
) : SuggestionRepository {

    override suspend fun getSuggestions(): List<SuggestionDto> {
        val userId = prefs.getUserId()
        return api.getSuggestions(userId)
    }

    override suspend fun createSuggestion(
        taskId: String?,
        message: String,
        reason: Map<String, Any>?,
        confidence: Double?
    ): SuggestionDto {
        val userId = prefs.getUserId()
        val dto = CreateSuggestionDto(
            user_id = userId,
            task_id = taskId,
            message = message,
            reason = reason,
            confidence = confidence
        )
        return api.createSuggestion(dto)
    }
}
