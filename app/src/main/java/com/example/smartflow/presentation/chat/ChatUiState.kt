package com.example.smartflow.presentation.chat

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ChatMessage(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isFromUser: Boolean
)