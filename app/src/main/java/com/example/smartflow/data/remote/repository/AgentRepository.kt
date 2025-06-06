package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.dto.AgentResponse

/**
 * Interfaz para invocar al agente (LangChain) remoto mediante FastAPI.
 */
interface AgentRepository {

    /**
     * Envía una consulta al agente LLM.
     * @param query Texto libre de la pregunta que el usuario hace al chat/agent.
     * @param taskId ID de la tarea en contexto (opcional; si se envía, el agente sabe en qué tarea contextualizar).
     * @return AgentResponse, que incluye el campo "result" con el JSON retornado por el agente.
     */
    suspend fun runAgent(
        query: String,
        taskId: String?
    ): AgentResponse
}
