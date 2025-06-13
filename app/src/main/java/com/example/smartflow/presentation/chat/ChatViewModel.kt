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

// ViewModel and data classes
class ChatViewModel : ViewModel() {
    private val _ui = MutableStateFlow(ChatUiState())
    val ui: StateFlow<ChatUiState> = _ui

    fun sendMessage(text: String) {
        val userMessage = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = text,
            isFromUser = true,
            timestamp = System.currentTimeMillis()
        )

        val currentMessages = _ui.value.messages.toMutableList()
        currentMessages.add(userMessage)

        // Simulate AI response
        val aiResponse = ChatMessage(
            id = (System.currentTimeMillis() + 1).toString(),
            text = "Thanks for your message! This is a placeholder AI response.",
            isFromUser = false,
            timestamp = System.currentTimeMillis() + 1000
        )
        currentMessages.add(aiResponse)

        _ui.value = _ui.value.copy(messages = currentMessages)
    }
}