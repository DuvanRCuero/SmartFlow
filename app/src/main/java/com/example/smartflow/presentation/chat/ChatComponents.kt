package com.example.smartflow.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartflow.ui.theme.BlueLight
import com.example.smartflow.ui.theme.GreyLightBg

@Composable
fun ChatBubble(msg: ChatMessage, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = if (msg.isUser) BlueLight else GreyLightBg
        ) {
            Text(
                msg.text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    Spacer(Modifier.height(8.dp))
}
