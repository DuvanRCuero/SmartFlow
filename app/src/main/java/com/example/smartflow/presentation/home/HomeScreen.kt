package com.example.smartflow.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smartflow.R           // pon tu logo en res/drawable/ic_logo_big.png
import com.example.smartflow.presentation.common.SfBottomBar
import com.example.smartflow.presentation.navigation.SfDestination

@Composable
fun HomeScreen(onSelectBottom: (SfDestination) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(Modifier.height(24.dp))
        androidx.compose.material3.Text(
            "Welcome to SmartFlow!",
            style = MaterialTheme.typography.titleMedium
        )
    }

    /* Bottom bar pegado al fondo */
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SfBottomBar(SfDestination.Home, onSelectBottom)
    }
}
