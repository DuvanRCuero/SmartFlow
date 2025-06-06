package com.example.smartflow.data.remote.api

import com.example.smartflow.data.remote.dto.SuggestionDto
import com.example.smartflow.data.remote.dto.CreateSuggestionDto
import retrofit2.http.*

interface SuggestionApi {

    @GET("suggestions")
    suspend fun getSuggestions(@Query("user_id") userId: String): List<SuggestionDto>

    @POST("suggestions")
    suspend fun createSuggestion(@Body body: CreateSuggestionDto): SuggestionDto
}
