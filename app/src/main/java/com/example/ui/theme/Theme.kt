package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CraftLauncherDarkColorScheme = darkColorScheme(
    primary = MinecraftGreenPrimary,
    onPrimary = DarkObsidianBg,
    primaryContainer = MinecraftGreenContainer,
    onPrimaryContainer = MinecraftGreenPrimary,
    secondary = DiamondCyan,
    onSecondary = DarkObsidianBg,
    secondaryContainer = DiamondCyanContainer,
    onSecondaryContainer = DiamondCyan,
    tertiary = MinecraftGold,
    onTertiary = DarkObsidianBg,
    tertiaryContainer = MinecraftGoldContainer,
    onTertiaryContainer = MinecraftGold,
    background = DarkObsidianBg,
    onBackground = TextPrimaryLight,
    surface = DarkSlateSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = DarkElevatedSurface,
    onSurfaceVariant = TextSecondaryMuted,
    outline = DarkCardBorder,
    error = StatusErrorRed
)

@Composable
fun CraftLauncherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CraftLauncherDarkColorScheme,
        typography = Typography,
        content = content
    )
}
