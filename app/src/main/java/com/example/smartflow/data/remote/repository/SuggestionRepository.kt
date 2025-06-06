package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.dto.SuggestionDto

/**
 * Interfaz para gestionar las sugerencias (Suggestion) remotas.
 */
interface SuggestionRepository {

    /**
     * Obtiene todas las sugerencias del usuario actual.
     * @return Lista de SuggestionDto
     */
    suspend fun getSuggestions(): List<SuggestionDto>

    /**
     * Crea una nueva sugerencia.
     * @param taskId ID de la tarea asociada (opcional)
     * @param message Texto de la sugerencia
     * @param reason JSON/diccionario con la razón (opcional)
     * @param confidence Valor de confianza (opcional)
     * @return El SuggestionDto recién creado
     */
    suspend fun createSuggestion(
        taskId: String?,
        message: String,
        reason: Map<String, Any>?,
        confidence: Double?
    ): SuggestionDto
}
