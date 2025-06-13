package com.example.smartflow.presentation.productivity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductivityViewModel : ViewModel() {

    private val _ui = MutableStateFlow(ProductivityUiState())
    val ui: StateFlow<ProductivityUiState> = _ui.asStateFlow()

    /** Mark one block as selected (only one at a time). */
    fun onBlockSelected(blockLabel: String) {
        _ui.value = _ui.value.copy(
            blocks = _ui.value.blocks.map { b -> b.copy(selected = b.label == blockLabel) }
        )
    }

    /** Pretend to fetch fresh analytics data. */
    fun refreshData() {
        _ui.value = _ui.value.copy(tips = freshTips())
    }

    // Demo-only replacement data
    private fun freshTips(): List<Tip> = listOf(
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
}
