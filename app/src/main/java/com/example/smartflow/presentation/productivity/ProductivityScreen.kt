package com.example.smartflow.presentation.productivity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.common.SfBottomBar
import com.example.smartflow.presentation.navigation.SfDestination
import com.example.smartflow.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductivityScreen(
    onBack: () -> Unit,
    onSelectBottom: (SfDestination) -> Unit,
    vm: ProductivityViewModel = viewModel()
) {
    val ui by vm.ui.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Productivity Analytics",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            SfBottomBar(
                selected = SfDestination.Productivity,
                onDestinationClick = onSelectBottom
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Overview Stats Card
            item {
                ProductivityOverviewCard(
                    completionRate = 85,
                    focusTime = "6h 32m",
                    tasksCompleted = 12,
                    peakHour = "10:00 AM"
                )
            }

            // Time Blocks Section
            item {
                Text(
                    text = "Time Blocks Performance",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(280.dp) // Fixed height to avoid nested scrolling
                ) {
                    items(ui.blocks) { block ->
                        EnhancedBlockCard(
                            label = block.label,
                            time = block.time,
                            selected = block.selected,
                            efficiency = block.efficiency,
                            onBlockClick = { vm.onBlockSelected(block.label) }
                        )
                    }
                }
            }

            // Weekly Trends Card
            item {
                WeeklyTrendsCard()
            }

            // AI Insights Section
            item {
                Text(
                    text = "AI Insights",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(ui.tips) { tip ->
                EnhancedTipCard(
                    title = tip.title,
                    description = tip.text,
                    subtitle = tip.sub,
                    icon = tip.icon,
                    priority = tip.priority
                )
            }

            // Focus Recommendations
            item {
                FocusRecommendationsCard()
            }
        }
    }
}

@Composable
private fun ProductivityOverviewCard(
    completionRate: Int,
    focusTime: String,
    tasksCompleted: Int,
    peakHour: String
) {
    SmartFlowCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Overview",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Surface(
                    shape = CircleShape,
                    color = SmartFlowTeal.copy(alpha = 0.1f)
                ) {
                    Icon(
                        Icons.Outlined.Analytics,
                        contentDescription = null,
                        tint = SmartFlowTeal,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OverviewMetric(
                    label = "Completion Rate",
                    value = "$completionRate%",
                    color = SmartFlowButtonBlue
                )
                OverviewMetric(
                    label = "Focus Time",
                    value = focusTime,
                    color = SmartFlowTeal
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OverviewMetric(
                    label = "Tasks Done",
                    value = tasksCompleted.toString(),
                    color = MaterialTheme.colorScheme.primary
                )
                OverviewMetric(
                    label = "Peak Hour",
                    value = peakHour,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun OverviewMetric(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun EnhancedBlockCard(
    label: String,
    time: String,
    selected: Boolean,
    efficiency: Int,
    onBlockClick: () -> Unit
) {
    SmartFlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onBlockClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                if (selected) {
                    Surface(
                        shape = CircleShape,
                        color = SmartFlowButtonBlue,
                        modifier = Modifier.size(8.dp)
                    ) {}
                }
            }

            // Efficiency indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LinearProgressIndicator(
                    progress = { efficiency / 100f },
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = when {
                        efficiency >= 80 -> SmartFlowTeal
                        efficiency >= 60 -> SmartFlowButtonBlue
                        else -> MaterialTheme.colorScheme.error
                    },
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Text(
                    text = "$efficiency%",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        efficiency >= 80 -> SmartFlowTeal
                        efficiency >= 60 -> SmartFlowButtonBlue
                        else -> MaterialTheme.colorScheme.error
                    }
                )
            }
        }
    }
}

@Composable
private fun WeeklyTrendsCard() {
    SmartFlowCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    Icons.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = SmartFlowTeal
                )
                Text(
                    text = "Weekly Trends",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEachIndexed { index, day ->
                    val height = listOf(60, 80, 45, 90, 75, 30, 40)[index]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(8.dp)
                                .height(height.dp)
                                .background(
                                    color = SmartFlowButtonBlue,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Text(
                text = "📈 +12% improvement from last week",
                style = MaterialTheme.typography.bodyMedium,
                color = SmartFlowTeal
            )
        }
    }
}

@Composable
private fun EnhancedTipCard(
    title: String,
    description: String,
    subtitle: String,
    icon: ImageVector,
    priority: TipPriority
) {
    SmartFlowCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = when (priority) {
                    TipPriority.HIGH -> MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                    TipPriority.MEDIUM -> SmartFlowButtonBlue.copy(alpha = 0.1f)
                    TipPriority.LOW -> SmartFlowTeal.copy(alpha = 0.1f)
                }
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = when (priority) {
                        TipPriority.HIGH -> MaterialTheme.colorScheme.error
                        TipPriority.MEDIUM -> SmartFlowButtonBlue
                        TipPriority.LOW -> SmartFlowTeal
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun FocusRecommendationsCard() {
    SmartFlowCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    Icons.Outlined.Psychology,
                    contentDescription = null,
                    tint = SmartFlowTeal
                )
                Text(
                    text = "Focus Recommendations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RecommendationItem(
                    title = "🌅 Morning Deep Work",
                    description = "Schedule complex tasks between 9-11 AM for peak performance"
                )
                RecommendationItem(
                    title = "☕ Post-Lunch Break",
                    description = "Take a 15-min break after lunch to maintain afternoon productivity"
                )
                RecommendationItem(
                    title = "📱 Digital Detox",
                    description = "Reduce phone usage during focus blocks by 23% for better concentration"
                )
            }
        }
    }
}

@Composable
private fun RecommendationItem(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(SmartFlowButtonBlue, CircleShape)
                .offset(y = 6.dp)
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}