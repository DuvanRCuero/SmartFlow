package com.example.smartflow.presentation.chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ChatMessage(
    val id: String,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long
)

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "1",
                content = "Scheduling a project meeting at your usual meeting time 1:30 to 2:30, let me know if you want any modifications to this meeting",
                isFromUser = false,
                timestamp = System.currentTimeMillis()
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _messageText = MutableStateFlow("")
    val messageText: StateFlow<String> = _messageText.asStateFlow()

    fun updateMessageText(text: String) {
        _messageText.value = text
    }

    fun sendMessage() {
        val currentText = messageText.value
        if (currentText.isNotEmpty()) {
            val newMessage = ChatMessage(
                id = System.currentTimeMillis().toString(),
                content = currentText,
                isFromUser = true,
                timestamp = System.currentTimeMillis()
            )
            _messages.update { currentMessages ->
                currentMessages + newMessage
            }
            _messageText.value = ""
        }
    }
}