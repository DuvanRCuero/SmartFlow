package com.example.smartflow.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartflow.domain.model.User
import com.example.smartflow.domain.usecase.LoginUseCase
import com.example.smartflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Form fields
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    // Validation errors
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError = _passwordError.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
        validateEmail()
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        validatePassword()
    }

    private fun validateEmail(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return when {
            _email.value.isEmpty() -> {
                _emailError.value = "Email cannot be empty"
                false
            }
            !_email.value.matches(emailPattern.toRegex()) -> {
                _emailError.value = "Enter a valid email address"
                false
            }
            else -> {
                _emailError.value = null
                true
            }
        }
    }

    private fun validatePassword(): Boolean {
        return when {
            _password.value.isEmpty() -> {
                _passwordError.value = "Password cannot be empty"
                false
            }
            _password.value.length < 6 -> {
                _passwordError.value = "Password must be at least 6 characters"
                false
            }
            else -> {
                _passwordError.value = null
                true
            }
        }
    }

    fun login() {
        // Validate form
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()

        if (!isEmailValid || !isPasswordValid) {
            return
        }

        // Attempt login
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                val result = loginUseCase(email.value, password.value)

                when (result) {
                    is Resource.Success -> {
                        Timber.d("Login successful for user: ${result.data.email}")
                        _uiState.value = LoginUiState.Success(result.data)
                    }
                    is Resource.Error -> {
                        Timber.e("Login failed: ${result.message}")
                        _uiState.value = LoginUiState.Error(result.message)
                    }
                    is Resource.Loading -> {
                        _uiState.value = LoginUiState.Loading
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Login exception")
                _uiState.value = LoginUiState.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Initial
    }
}