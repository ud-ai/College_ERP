package com.example.collegeerp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ThemeToggleButton(
    isDarkMode: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onToggle(!isDarkMode) },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isDarkMode) Icons.Default.Star else Icons.Default.Settings,
            contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
            tint = if (isDarkMode) Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurface
        )
    }
}