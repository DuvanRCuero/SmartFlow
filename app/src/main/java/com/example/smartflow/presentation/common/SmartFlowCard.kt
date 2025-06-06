package com.example.smartflow.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartflow.ui.theme.GreyStroke

@Composable
fun SmartFlowCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(),
        border = BorderStroke(width = 1.dp, color = GreyStroke)
    ) { content() }
}