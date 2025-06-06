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
    Font(R.font.inter_regular,  FontWeight.Normal),
    Font(R.font.inter_medium,   FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold,     FontWeight.Bold),
)

private val LightScheme = lightColorScheme(
    primary        = BluePrimary,
    onPrimary      = Color.White,
    surface        = Color.White,
    onSurface      = Color(0xFF1B1F29),
    surfaceVariant = GreyLightBg,
    outline        = GreyStroke,
    secondary      = GreyIcon,
    tertiary       = SuccessGreen,
    error          = ErrorRed
)

@Composable
fun SmartFlowTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightScheme, // swap with darkScheme if needed
        typography  = Typography(
            bodyLarge      = MaterialTheme.typography.bodyLarge.copy(fontFamily = Inter),
            bodyMedium     = MaterialTheme.typography.bodyMedium.copy(fontFamily = Inter),
            bodySmall      = MaterialTheme.typography.bodySmall.copy(fontFamily = Inter),
            titleLarge     = MaterialTheme.typography.titleLarge.copy(fontFamily = Inter),
            titleMedium    = MaterialTheme.typography.titleMedium.copy(fontFamily = Inter),
            labelLarge     = MaterialTheme.typography.labelLarge.copy(fontFamily = Inter)
        ),
        shapes = Shapes(
            small  = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large  = RoundedCornerShape(20.dp)
        ),
        content = content
    )
}
