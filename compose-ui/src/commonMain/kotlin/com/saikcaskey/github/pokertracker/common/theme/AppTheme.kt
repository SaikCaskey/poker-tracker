package com.saikcaskey.github.pokertracker.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.rememberDynamicColorScheme

@Composable
fun AppTheme(
    seedColor: Color,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = rememberDynamicColorScheme(
        primary = seedColor,
        isDark = useDarkTheme,
        style = PaletteStyle.FruitSalad
    )
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}