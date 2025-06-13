package com.example.smartflow.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartflow.data.remote.dto.AgentResponse
import com.example.smartflow.data.remote.repository.AgentRepository
import com.example.smartflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(this.data))
        is Resource.Error -> Resource.Error(this.message)
        Resource.Loading -> Resource.Loading
    }
}

fun parseResponseToMap(response: String): Map<String, Any> {
    return try {
        val json = Json { ignoreUnknownKeys = true }
        val jsonElement = json.parseToJsonElement(response)

        // Convert JsonObject to Map<String, Any>
        if (jsonElement is JsonObject) {
            jsonElement.entries.associate { (key, value) ->
                key to when {
                    value.jsonPrimitive.isString -> value.jsonPrimitive.content
                    else -> value.toString().removeSurrounding("\"")
                }
            }
        } else {
            emptyMap()
        }
    } catch (e: Exception) {
        emptyMap()
    }
}

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val agentRepo: AgentRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(ChatUiState())
    val ui: StateFlow<ChatUiState> = _ui

    fun sendMessage(txt: String) = viewModelScope.launch {
        if (txt.isBlank()) return@launch

        _ui.value = _ui.value.copy(
            messages = _ui.value.messages + ChatMessage(txt, true)
        )

        _ui.value = _ui.value.copy(loading = true, error = null)

        val res: Resource<AgentResponse> = agentRepo.runAgent(txt, taskId = null).map { apiResponse ->
            AgentResponse(
                result = parseResponseToMap(apiResponse.response)
            )
        }

        when (res) {
            is Resource.Success -> {
                val reply = res.data?.result?.get("raw")?.toString() ?: "…"
                _ui.value = _ui.value.copy(
                    messages = _ui.value.messages + ChatMessage(reply, false),
                    loading = false
                )
            }
            is Resource.Error -> {
                _ui.value = _ui.value.copy(
                    loading = false,
                    error = res.message ?: "Error"
                )
            }
            Resource.Loading -> Unit
        }
    }
}