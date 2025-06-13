package com.example.smartflow.presentation.productivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.ui.theme.*

// Backward compatibility wrapper for existing BlockCard usage
@Composable
fun BlockCard(
    label: String,
    time: String,
    selected: Boolean
) {
    SmartFlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) SmartFlowButtonBlue else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            if (selected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(SmartFlowButtonBlue, CircleShape)
                        .align(Alignment.End)
                )
            }
        }
    }
}

// Backward compatibility wrapper for existing TipCard usage
@Composable
fun TipCard(
    text: String,
    sub: String
) {
    SmartFlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = SmartFlowButtonBlue.copy(alpha = 0.1f)
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = SmartFlowButtonBlue,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = sub,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// Productivity metrics card for dashboard integration
@Composable
fun ProductivityMetricsCard(
    todayEfficiency: Int,
    weeklyGoalProgress: Float,
    modifier: Modifier = Modifier
) {
    SmartFlowCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Productivity",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$todayEfficiency%",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = SmartFlowButtonBlue
                    )
                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${(weeklyGoalProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = SmartFlowTeal
                    )
                    Text(
                        text = "Weekly Goal",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            LinearProgressIndicator(
                progress = { weeklyGoalProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = SmartFlowTeal,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

// Quick productivity action buttons
@Composable
fun ProductivityActionsCard(
    onStartFocusSession: () -> Unit,
    onTakeBreak: () -> Unit,
    onViewAnalytics: () -> Unit,
    modifier: Modifier = Modifier
) {
    SmartFlowCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onStartFocusSession,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Focus")
                }

                OutlinedButton(
                    onClick = onTakeBreak,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Break")
                }

                OutlinedButton(
                    onClick = onViewAnalytics,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Analytics")
                }
            }
        }
    }
}

// Time block efficiency indicator
@Composable
fun EfficiencyIndicator(
    efficiency: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "$efficiency%",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = when {
                efficiency >= 80 -> SmartFlowTeal
                efficiency >= 60 -> SmartFlowButtonBlue
                else -> MaterialTheme.colorScheme.error
            }
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        LinearProgressIndicator(
            progress = { efficiency / 100f },
            modifier = Modifier
                .width(40.dp)
                .height(3.dp),
            color = when {
                efficiency >= 80 -> SmartFlowTeal
                efficiency >= 60 -> SmartFlowButtonBlue
                else -> MaterialTheme.colorScheme.error
            },
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}