package com.example.smartflow.presentation.productivity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TimeBlock(
    val id: String,
    val title: String,
    val timeRange: String,
    val iconResId: Int // Resource ID for the icon
)

data class ProductivityTip(
    val id: String,
    val tip: String,
    val details: String
)

class ProductivityViewModel : ViewModel() {
    // In a real app, these would use actual resource IDs
    private val _timeBlocks = MutableStateFlow(
        listOf(
            TimeBlock(
                id = "1",
                title = "Morning",
                timeRange = "9:00 AM - 12:00 PM",
                iconResId = MISSING_ICON // Placeholder for missing icon resources
            ),
            TimeBlock(
                id = "2",
                title = "Afternoon",
                timeRange = "12:00 PM - 5:00 PM",
                iconResId = MISSING_ICON // Placeholder for missing icon resources
            ),
            TimeBlock(
                id = "3",
                title = "Evening",
                timeRange = "5:00 PM - 9:00 PM",
                iconResId = MISSING_ICON // Placeholder for missing icon resources
            ),
            TimeBlock(
                id = "4",
                title = "Night",
                timeRange = "9:00 PM - 10:00 PM",
                iconResId = MISSING_ICON // Placeholder for missing icon resources
            ),
            TimeBlock(
                id = "5",
                title = "Late Night",
                timeRange = "10:00 PM - 12:00 AM",
                iconResId = MISSING_ICON // Placeholder for missing icon resources
            )
        )
    )
    val timeBlocks: StateFlow<List<TimeBlock>> = _timeBlocks.asStateFlow()

    private val _productivityTips = MutableStateFlow(
        listOf(
            ProductivityTip(
                id = "1",
                tip = "You avoid complex tasks after lunch",
                details = "Complex tasks: 0/8"
            ),
            ProductivityTip(
                id = "2",
                tip = "Task completion rate: 80% in mornings",
                details = "Mornings: 9:00 AM - 12:00 PM"
            )
        )
    )
    val productivityTips: StateFlow<List<ProductivityTip>> = _productivityTips.asStateFlow()
}