package com.example.smartflow.presentation.productivity

data class Block(val label: String, val time: String, val selected: Boolean = false)
data class Tip(val id: String, val text: String, val sub: String)

data class ProductivityUiState(
    val blocks: List<Block> = listOf(
        Block("Morning", "9:00 AM – 12:00 PM", true),
        Block("Afternoon", "12:00 PM – 2:00 PM"),
        Block("Evening", "2:00 PM – 6:00 PM"),
        Block("Night", "6:00 PM – 10:00 PM"),
        Block("Late Night", "10:00 PM – 12:00 AM")
    ),
    val tips: List<Tip> = listOf(
        Tip("1", "You avoid complex tasks after lunch", "Complex tasks: 0.8"),
        Tip("2", "Task completion rate: 80% in mornings", "Mornings: 9:00 AM – 12:00 PM")
    )
)
