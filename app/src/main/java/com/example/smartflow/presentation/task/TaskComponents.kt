package com.example.smartflow.presentation.task

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.ui.theme.*

/* ------------------------------------------------------------------ */
/* Mini-chart place-holder (replace with MPAndroidChart if you want)  */
/* ------------------------------------------------------------------ */
@Composable
fun TaskLineChart(
    modifier: Modifier = Modifier,
    progress: Float = 0.5f          // 0-1 convenience if you only need one point
) {
    Canvas(
        modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        drawLine(
            color       = SmartFlowButtonBlue,
            start       = Offset(0f, size.height * (1 - progress)),
            end         = Offset(size.width, size.height * progress),
            strokeWidth = 6f
        )
    }
}

/* ------------------------------------------------------------------ */
/* Insight card used in Task screen                                   */
/* ------------------------------------------------------------------ */
@Composable
fun InsightCard(title: String, subtitle: String) {
    SmartFlowCard(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(Modifier.weight(1f)) {
                androidx.compose.material3.Text(title, style = MaterialTheme.typography.titleMedium)
                androidx.compose.material3.Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Gray)
            }
            Icon(Icons.Outlined.Add, contentDescription = null)
        }
    }
}
