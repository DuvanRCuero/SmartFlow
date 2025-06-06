package com.example.smartflow.presentation.common

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun SfScaffold(
    bottomBar: @Composable (() -> Unit) = {},
    content: @Composable (androidx.compose.foundation.layout.PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = bottomBar,
        content = content
    )
}
