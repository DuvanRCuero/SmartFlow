package com.example.smartflow.data.remote.repository

import com.example.smartflow.data.remote.dto.TaskDto

/**
 * Interfaz para gestionar las tareas (Task) remotas.
 * Sus implementaciones (por ejemplo TaskRepositoryImpl) usarán Retrofit+FastAPI.
 */
interface TaskRepository {

    /**
     * Obtiene todas las tareas del usuario actual.
     * @return Lista de TaskDto
     */
    suspend fun getTasks(): List<TaskDto>

    /**
     * Crea una nueva tarea con los datos provistos.
     * @param title Título de la tarea
     * @param description Descripción (opcional)
     * @param dueAt Fecha de vencimiento en ISO-8601 (opcional)
     * @param estMinutes Tiempo estimado en minutos (opcional)
     * @param energyReq Nivel de energía requerido (opcional)
     * @param priority Prioridad (1..9) (opcional)
     * @return El TaskDto recién creado
     */
    suspend fun createTask(
        title: String,
        description: String?,
        dueAt: String?,
        estMinutes: Int?,
        energyReq: String?,
        priority: Int?
    ): TaskDto

    /**
     * Actualiza una tarea existente.
     * @param taskId ID de la tarea a actualizar
     * @param title Nuevo título (o null para no cambiar)
     * @param description Nueva descripción (o null para no cambiar)
     * @param dueAt Nueva fecha de vencimiento en ISO-8601 (o null)
     * @param estMinutes Nuevo tiempo estimado (o null)
     * @param energyReq Nuevo nivel de energía (o null)
     * @param priority Nueva prioridad (o null)
     * @param state Nuevo estado (por ejemplo "pending", "done") (o null)
     * @return El TaskDto actualizado
     */
    suspend fun updateTask(
        taskId: String,
        title: String?,
        description: String?,
        dueAt: String?,
        estMinutes: Int?,
        energyReq: String?,
        priority: Int?,
        state: String?
    ): TaskDto

    /**
     * Elimina la tarea con ID = taskId.
     * No devuelve nada (lanza excepción si hubo error).
     */
    suspend fun deleteTask(taskId: String)
}
