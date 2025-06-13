package com.example.smartflow.data.remote.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface SuggestionApi {
    @GET("suggestions")
    suspend fun getSuggestions(): Response<List<SuggestionResponse>>

    @POST("suggestions/generate")
    suspend fun generateSuggestions(@Body request: GenerateSuggestionsRequest): Response<List<SuggestionResponse>>

    @GET("suggestions/{id}")
    suspend fun getSuggestion(@Path("id") id: String): Response<SuggestionResponse>

    @DELETE("suggestions/{id}")
    suspend fun deleteSuggestion(@Path("id") id: String): Response<Unit>

    @GET("suggestions/user/{userId}")
    suspend fun getUserSuggestions(@Path("userId") userId: String): Response<List<SuggestionResponse>>
}

@Serializable
data class SuggestionResponse(
    val id: String,
    val title: String,
    val description: String,
    val category: String? = null,
    val priority: String = "medium", // low, medium, high
    val estimated_time: String? = null,
    val created_at: String,
    val user_id: String? = null
)

@Serializable
data class GenerateSuggestionsRequest(
    val context: String,
    val task_type: String? = null,
    val max_suggestions: Int = 5
)