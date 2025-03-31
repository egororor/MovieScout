package org.example.project.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MovieDatabaseAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme  // ✅ Fully defined in commonMain

    MaterialTheme(
        colors = colors,
        typography = AppTypography,  // ✅ Defined in commonMain
        content = content
    )
}