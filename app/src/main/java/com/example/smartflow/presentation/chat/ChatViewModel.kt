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
import javax.inject.Inject

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

        val res: Resource<AgentResponse> = agentRepo.runAgent(txt, taskId = null)

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
