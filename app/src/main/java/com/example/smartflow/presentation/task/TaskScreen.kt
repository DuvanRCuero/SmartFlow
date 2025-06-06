package com.example.smartflow.presentation.task

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartflow.presentation.common.SfBottomBar

import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.navigation.SfDestination
import com.example.smartflow.presentation.theme.*
import com.example.smartflow.ui.theme.SmartFlowButtonBlue
import com.example.smartflow.ui.theme.SmartFlowTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    vm: TaskViewModel = viewModel(),
    onSelectBottom: (SfDestination) -> Unit,
    onBack: () -> Unit
) {
    val ui by vm.ui.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* vacío (logo ya en app bar?) */ },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                }
            )
        },
        bottomBar = { SfBottomBar(SfDestination.Tasks, onSelectBottom) }
    ) { pv ->
        Column(
            modifier = Modifier
                .padding(pv)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Header
            Text(
                "Tasks Completed",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = ui.completed.toString(),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Past 5 weeks  ${if (ui.deltaPercent >= 0) "+" else ""}${ui.deltaPercent}%",
                color = SmartFlowTeal,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Chart placeholder
            Canvas(
                Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(16.dp)
            ) {
                // TODO: reemplazar con librería real
                drawLine(
                    color = SmartFlowButtonBlue,
                    start = Offset(0f, size.height * 0.7f),
                    end = Offset(size.width, size.height * 0.3f),
                    strokeWidth = 6f
                )
            }

            // Tabs Week 1 – Week 5
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(5) { i ->
                    Text("Week ${i + 1}", style = MaterialTheme.typography.labelLarge)
                }
            }

            Divider(Modifier.padding(vertical = 12.dp))

            // Insights
            ui.insights.forEach { ins ->
                SmartFlowCard(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(ins.title, style = MaterialTheme.typography.titleMedium)
                            Text(
                                ins.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Icon(Icons.Outlined.Add, null) // + icon en Figma
                    }
                }
            }
        }
    }
}
