package com.example.smartflow.presentation.auth

data class LoginUiState(
    val email: String        = "",
    val password: String     = "",
    val loading: Boolean     = false,
    val error: String?       = null
) {
    val canLogin: Boolean
        get() = email.isNotBlank()
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length >= 6
}
