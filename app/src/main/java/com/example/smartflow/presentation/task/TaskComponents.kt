package com.example.smartflow.presentation.task

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.theme.*

@Composable
fun TaskCompletionChart(weeklyTasksData: List<Int>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Simple chart implementation
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val maxValue = weeklyTasksData.maxOrNull() ?: 1
            val padding = 32f

            // Draw chart line
            val chartPath = Path()
            val points = weeklyTasksData.mapIndexed { index, value ->
                val x = padding + (width - 2 * padding) * index / (weeklyTasksData.size - 1)
                val y = height - padding - (height - 2 * padding) * value / maxValue
                Offset(x, y)
            }

            // Start the path
            if (points.isNotEmpty()) {
                chartPath.moveTo(points.first().x, points.first().y)
            }

            // Add points to the path
            for (i in 1 until points.size) {
                chartPath.lineTo(points[i].x, points[i].y)
            }

            // Draw the path
            drawPath(
                path = chartPath,
                color = ChartLineBlue,
                style = Stroke(
                    width = 3f,
                    cap = StrokeCap.Round
                )
            )

            // Draw points
            points.forEach { point ->
                drawCircle(
                    color = ChartLineBlue,
                    radius = 4f,
                    center = point
                )
            }
        }

        // Week indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..5) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Week $i",
                        color = White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    title: String,
    subtitle: String,
    showAddButton: Boolean = false
) {
    SmartFlowCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )
            }

            if (showAddButton) {
                IconButton(
                    onClick = { 
                        // TODO: Implement add task functionality
                        println("Add button clicked") 
                    },
                    modifier = Modifier
                        .size(32.dp)
                        .background(SmartFlowTeal, RoundedCornerShape(16.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add task",
                        tint = White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}