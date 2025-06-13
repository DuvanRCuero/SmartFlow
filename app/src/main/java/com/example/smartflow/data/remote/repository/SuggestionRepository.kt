package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.SuggestionResponse
import com.example.smartflow.data.remote.api.GenerateSuggestionsRequest
import com.example.smartflow.util.Resource

interface SuggestionRepository {
    suspend fun getSuggestions(): Resource<List<SuggestionResponse>>
    suspend fun generateSuggestions(request: GenerateSuggestionsRequest): Resource<List<SuggestionResponse>>
    suspend fun getSuggestion(id: String): Resource<SuggestionResponse>
    suspend fun deleteSuggestion(id: String): Resource<Unit>
}
