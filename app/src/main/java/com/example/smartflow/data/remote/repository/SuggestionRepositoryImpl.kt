// data/remote/repository/SuggestionRepositoryImpl.kt
package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.api.SuggestionApi
import com.example.smartflow.data.remote.api.SuggestionResponse
import com.example.smartflow.data.remote.api.GenerateSuggestionsRequest
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuggestionRepositoryImpl @Inject constructor(
    private val api: SuggestionApi,
    private val prefs: AuthPreferences
) : SuggestionRepository {

    override suspend fun getSuggestions(): Resource<List<SuggestionResponse>> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getSuggestions()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get suggestions: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun generateSuggestions(request: GenerateSuggestionsRequest): Resource<List<SuggestionResponse>> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.generateSuggestions(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to generate suggestions: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun getSuggestion(id: String): Resource<SuggestionResponse> {
        return try {
            val userId = prefs.userId.first()
            if (userId.isNullOrEmpty()) {
                return Resource.Error("User not authenticated")
            }

            val response = api.getSuggestion(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to get suggestion: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun deleteSuggestion(id: String): Resource<Unit> {
        TODO("Not yet implemented")
    }
}
