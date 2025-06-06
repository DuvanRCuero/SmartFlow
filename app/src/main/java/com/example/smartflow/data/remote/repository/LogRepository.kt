package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.dto.LogDto

/**
 * Interfaz para gestionar los logs de productividad (ProductivityLog) remotos.
 */
interface LogRepository {

    /**
     * Obtiene los últimos registros de productividad del usuario.
     * @return Lista de LogDto
     */
    suspend fun getLogs(): List<LogDto>

    /**
     * Crea un nuevo registro de productividad.
     * @param focusScore Puntaje de foco (0.0 - 1.0)
     * @param energyLevel Nivel de energía (p.ej. "low", "medium", "high")
     * @param context Información adicional en formato Map (opcional)
     * @return El LogDto recién creado
     */
    suspend fun createLog(
        focusScore: Double,
        energyLevel: String,
        context: Map<String, Any>?
    ): LogDto
}
