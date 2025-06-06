package com.example.smartflow.presentation.productivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.ui.theme.*

/* ------------------------------------------------------------------ */
/* 2×2 (o 3×2) time-of-day block                                      */
/* ------------------------------------------------------------------ */
@Composable
fun BlockCard(label: String, time: String, selected: Boolean) {
    SmartFlowCard(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                if (selected) BlueLight else androidx.compose.ui.graphics.Color.Transparent,
                MaterialTheme.shapes.medium
            )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material3.Text(label, style = MaterialTheme.typography.titleMedium)
            androidx.compose.material3.Text(time, style = MaterialTheme.typography.bodySmall, color = Gray)
        }
    }
}

/* ------------------------------------------------------------------ */
/* Tip-card with light left icon                                      */
/* ------------------------------------------------------------------ */
@Composable
fun TipCard(text: String, sub: String) {
    SmartFlowCard(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = null,
                tint = SmartFlowButtonBlue,
                modifier = Modifier
                    .size(20.dp)
                    .background(BlueLight, CircleShape)
                    .padding(2.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                androidx.compose.material3.Text(text, style = MaterialTheme.typography.bodyMedium)
                androidx.compose.material3.Text(sub, style = MaterialTheme.typography.bodySmall, color = Gray)
            }
        }
    }
}
