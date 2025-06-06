package com.example.smartflow.presentation.productivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartflow.presentation.common.SmartFlowCard
import com.example.smartflow.presentation.common.SfBottomBar
import com.example.smartflow.presentation.navigation.SfDestination
import com.example.smartflow.ui.theme.*

/* -------------------------------------------------------------------------- */
/* Pantalla Productivity                                                      */
/* -------------------------------------------------------------------------- */
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
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { SfBottomBar(SfDestination.Productivity, onSelectBottom) }
    ) { pv ->
        Column(
            modifier = Modifier
                .padding(pv)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            /* ----------- Bloques de tiempo (2 columnas) ----------- */
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(ui.blocks) { block ->
                    SmartFlowCard(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                if (block.selected) BlueLight
                                else androidx.compose.ui.graphics.Color.Transparent,
                                shape = MaterialTheme.shapes.medium
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(block.label, style = MaterialTheme.typography.titleMedium)
                            Text(block.time, style = MaterialTheme.typography.bodySmall, color = Gray)
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            /* ------------------- Tips / Insights ------------------ */
            ui.tips.forEach { tip ->
                SmartFlowCard(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = SmartFlowButtonBlue
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(tip.text, style = MaterialTheme.typography.bodyMedium)
                            Text(tip.sub, style = MaterialTheme.typography.bodySmall, color = Gray)
                        }
                    }
                }
            }
        }
    }
}
