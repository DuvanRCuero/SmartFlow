package com.example.smartflow.presentation.productivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.theme.*

@Composable
fun TimeBlockCard(
    title: String,
    timeRange: String,
    iconResId: Int
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
            // In a real app, use the actual icon from resources
            if (iconResId != 0) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = title,
                    modifier = Modifier.size(40.dp)
                )
            } else {
                // Placeholder if no icon is available
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(LightGray, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title.first().toString(),
                        color = BackgroundDark
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = timeRange,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )
            }
        }
    }
}

@Composable
fun ProductivityTipCard(
    tip: String,
    details: String
) {
    SmartFlowCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(SmartFlowTeal, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔍",
                    textAlign = TextAlign.Center
                )
            }

            Column {
                Text(
                    text = tip,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )
            }
        }
    }
}