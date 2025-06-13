package com.example.smartflow.presentation.productivity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

// ---------- UI-state & data models ----------

data class ProductivityUiState(
    val blocks: List<Block> = sampleBlocks(),
    val tips: List<Tip> = sampleTips(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class Block(
    val label: String,
    val time: String,
    val selected: Boolean = false,
    val efficiency: Int = 75,   // percentage
    val tasksCompleted: Int = 0,
    val totalTasks: Int = 0
)

data class Tip(
    val id: String,
    val title: String,
    val text: String,
    val sub: String,
    val icon: ImageVector = Icons.Outlined.Info,
    val priority: TipPriority = TipPriority.MEDIUM
)

enum class TipPriority { HIGH, MEDIUM, LOW }

// ---------- sample data helpers (for previews / demo) ----------

private fun sampleBlocks(): List<Block> = listOf(
    Block("Morning",  "9:00 AM – 12:00 PM", selected = true,  efficiency = 92, tasksCompleted = 8, totalTasks = 10),
    Block("Afternoon","12:00 PM – 2:00 PM", selected = false, efficiency = 68, tasksCompleted = 3, totalTasks = 5),
    Block("Evening",  "2:00 PM – 6:00 PM",  selected = false, efficiency = 75, tasksCompleted = 6, totalTasks = 8),
    Block("Night",    "6:00 PM – 10:00 PM", selected = false, efficiency = 45, tasksCompleted = 2, totalTasks = 6),
    Block("Late Night","10:00 PM – 12:00 AM",selected = false, efficiency = 30, tasksCompleted = 1, totalTasks = 4)
)

private fun sampleTips(): List<Tip> = listOf(
    Tip(
        id = "1",
        title = "Peak Performance Hours",
        text = "You avoid complex tasks after lunch",
        sub  = "Complex-task completion is 80 % higher in mornings",
        icon = Icons.Outlined.TrendingUp,
        priority = TipPriority.HIGH
    ),
    Tip(
        id = "2",
        title = "Task Completion Patterns",
        text = "Task-completion rate: 85 % in mornings",
        sub  = "Best performance: 9:00 AM – 12:00 PM",
        icon = Icons.Outlined.Schedule,
        priority = TipPriority.MEDIUM
    ),
    Tip(
        id = "3",
        title = "Focus-Duration Insights",
        text = "Your focus sessions average 45 minutes",
        sub  = "Recommendation: 10-min breaks between sessions",
        icon = Icons.Outlined.Psychology,
        priority = TipPriority.LOW
    ),
    Tip(
        id = "4",
        title = "Weekly Progress",
        text = "Productivity increased by 12 % this week",
        sub  = "Consistent improvement in task completion",
        icon = Icons.Outlined.Info,
        priority = TipPriority.MEDIUM
    )
)
