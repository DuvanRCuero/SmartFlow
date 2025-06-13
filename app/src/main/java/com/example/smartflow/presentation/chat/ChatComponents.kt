package com.example.smartflow.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartflow.ui.theme.SmartFlowButtonBlue

@Composable
fun ChatBubble(msg: ChatMessage, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (msg.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (msg.isFromUser) {
            Spacer(modifier = Modifier.width(48.dp))
        }

        Surface(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (msg.isFromUser) 16.dp else 4.dp,
                bottomEnd = if (msg.isFromUser) 4.dp else 16.dp
            ),
            color = if (msg.isFromUser) {
                SmartFlowButtonBlue
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            shadowElevation = 1.dp
        ) {
            Text(
                text = msg.text,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = if (msg.isFromUser) {
                    Color.White
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }

        if (!msg.isFromUser) {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}