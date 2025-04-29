package com.example.smartflow.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartflow.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SignupUiState {
    object Idle    : SignupUiState
    object Loading : SignupUiState
    object Success : SignupUiState
    data class Error(val message: String) : SignupUiState
}

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _email    = MutableStateFlow("")
    private val _name     = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _uiState  = MutableStateFlow<SignupUiState>(SignupUiState.Idle)

    val email    : StateFlow<String>        = _email
    val name     : StateFlow<String>        = _name
    val password : StateFlow<String>        = _password
    val uiState  : StateFlow<SignupUiState> = _uiState

    fun onEmailChange(v: String)    { _email.value = v }
    fun onNameChange(v: String)     { _name.value = v }
    fun onPasswordChange(v: String) { _password.value = v }

    fun signup() {
        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading
            repo.register(_email.value, _password.value, _name.value)
                .onSuccess {
                    _uiState.value = SignupUiState.Success
                }
                .onFailure {
                    _uiState.value = SignupUiState.Error(it.message ?: "Error inesperado")
                }
        }
    }
}
