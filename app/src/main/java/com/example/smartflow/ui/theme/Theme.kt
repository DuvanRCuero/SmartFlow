package com.example.smartflow.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Your color definitions (in the same package)
import com.example.smartflow.presentation.theme.SmartFlowTeal
import com.example.smartflow.presentation.theme.SmartFlowButtonBlue
import com.example.smartflow.presentation.theme.BackgroundDark
import com.example.smartflow.presentation.theme.White
import com.example.smartflow.presentation.theme.Black

// Your typography & shapes
import com.example.smartflow.presentation.theme.Typography
import com.example.smartflow.presentation.theme.Shapes

private val DarkColorScheme = darkColorScheme(
    primary        = SmartFlowTeal,
    secondary      = SmartFlowButtonBlue,
    background     = BackgroundDark,
    surface        = White,
    onPrimary      = White,
    onSecondary    = White,
    onBackground   = White,
    onSurface      = Black
)

private val LightColorScheme = lightColorScheme(
    primary        = SmartFlowTeal,
    secondary      = SmartFlowButtonBlue,
    background     = White,
    surface        = White,
    onPrimary      = White,
    onSecondary    = White,
    onBackground   = Black,
    onSurface      = Black
)

@Composable
fun SmartFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor =
                BackgroundDark.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        shapes      = Shapes,
        content     = content
    )
}
