package com.myexpense.expensetracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4F46E5),
    onPrimary = Color.White,

    secondary = Color(0xFF7C3AED),
    onSecondary = Color.White,

    tertiary = Color(0xFFF59E0B),
    onTertiary = Color(0xFF171717),

    background = Color(0xFFF8F9FF),
    onBackground = Color(0xFF111827),

    surface = Color.White,
    onSurface = Color(0xFF111827),

    surfaceVariant = Color(0xFFEDE9FE),
    onSurfaceVariant = Color(0xFF6B7280),

    error = Color(0xFFDC2626),
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9B91FF),
    onPrimary = Color(0xFF17133D),

    secondary = Color(0xFFB794F4),
    onSecondary = Color(0xFF24103D),

    tertiary = Color(0xFFFFC857),
    onTertiary = Color(0xFF2D2100),

    background = Color(0xFF0F1117),
    onBackground = Color(0xFFF3F4F6),

    surface = Color(0xFF181B24),
    onSurface = Color(0xFFF3F4F6),

    surfaceVariant = Color(0xFF292D3A),
    onSurfaceVariant = Color(0xFFB8BBC6),

    error = Color(0xFFFF6B6B),
    onError = Color(0xFF3B0909)
)

@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ExpenseTrackerTypography,
        content = content
    )
}