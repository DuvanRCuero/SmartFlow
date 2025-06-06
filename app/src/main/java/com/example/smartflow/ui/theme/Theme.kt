package com.example.smartflow.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartflow.R

private val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

private val LightScheme = lightColorScheme(
    primary = SmartFlowButtonBlue,
    onPrimary = White,
    surface = White,
    onSurface = Color(0xFF1B1F29),
    surfaceVariant = GreyLightBg,
    outline = GreyStroke,
    secondary = Gray,
    tertiary = SmartFlowTeal,
    error = ErrorRed
)

@Composable
fun SmartFlowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightScheme,
        typography = Typography().run {
            copy(
                bodyMedium = bodyMedium.copy(fontFamily = Inter),
                bodySmall  = bodySmall.copy(fontFamily = Inter),
                titleMedium = titleMedium.copy(fontFamily = Inter),
                labelLarge  = labelLarge.copy(fontFamily = Inter)
            )
        },
        shapes = Shapes(
            small  = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large  = RoundedCornerShape(20.dp)
        ),
        content = content
    )
}