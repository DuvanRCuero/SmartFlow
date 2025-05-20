package com.example.smartflow.presentation.productivity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartflow.presentation.common.ScreenScaffold

@Composable
fun ProductivityScreen(
    onBackClick: () -> Unit = {},
    productivityViewModel: ProductivityViewModel = viewModel()
) {
    val timeBlocks by productivityViewModel.timeBlocks.collectAsState()
    val productivityTips by productivityViewModel.productivityTips.collectAsState()

    ScreenScaffold(
        title = "Productivity",
        onBackClick = onBackClick
    ) {
        // Productivity time blocks
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Time blocks
            items(timeBlocks) { timeBlock ->
                TimeBlockCard(
                    title = timeBlock.title,
                    timeRange = timeBlock.timeRange,
                    iconResId = timeBlock.iconResId
                )
            }

            // Productivity Tips
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    productivityTips.forEach { tip ->
                        ProductivityTipCard(
                            tip = tip.tip,
                            details = tip.details
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}