package com.example.smartflow.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(LoginUiState())
    val ui: StateFlow<LoginUiState> = _ui

    fun setEmail(txt: String)    { _ui.value = _ui.value.copy(email = txt, error = null) }
    fun setPassword(pw: String)  { _ui.value = _ui.value.copy(password = pw, error = null) }

    fun login(onSuccess: () -> Unit) = viewModelScope.launch {
        _ui.value = _ui.value.copy(loading = true, error = null)

        when (val res = authRepo.login(_ui.value.email, _ui.value.password)) {
            is Resource.Success -> onSuccess()
            is Resource.Error   -> _ui.value = _ui.value.copy(error = res.message)
            Resource.Loading    -> Unit
        }
        _ui.value = _ui.value.copy(loading = false)
    }
}
