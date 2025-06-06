package com.example.smartflow.presentation.productivity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ProductivityViewModel : ViewModel() {
    private val _ui = MutableStateFlow(ProductivityUiState())
    val ui: StateFlow<ProductivityUiState> = _ui
}
