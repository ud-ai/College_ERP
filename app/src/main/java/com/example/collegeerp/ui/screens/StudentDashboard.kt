package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboard(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToAttendance: () -> Unit = {},
    onNavigateToMarks: () -> Unit = {},
    onNavigateToFees: () -> Unit = {},
    themeManager: com.example.collegeerp.ui.theme.ThemeManager? = null,
    onSignOut: () -> Unit = {}
) {
    Scaffold(
        bottomBar = { 
            StudentBottomNavigation(
                onNavigateToProfile = onNavigateToProfile,
                onSignOut = onSignOut
            ) 
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Welcome back",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Student Portal",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        themeManager?.let {
                            val isDarkMode by it.isDarkMode.collectAsState(initial = false)
                            com.example.collegeerp.ui.components.ThemeToggleButton(
                                isDarkMode = isDarkMode,
                                onToggle = { it.toggleTheme() }
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Live Stats Section
                Text(
                    text = "Your Progress",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    com.example.collegeerp.ui.components.InteractiveStatsCard(
                        title = "Attendance",
                        value = "87%",
                        change = "+3%",
                        isPositive = true,
                        icon = Icons.Default.DateRange,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    com.example.collegeerp.ui.components.InteractiveStatsCard(
                        title = "CGPA",
                        value = "8.4",
                        change = "+0.2",
                        isPositive = true,
                        icon = Icons.Default.Star,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                com.example.collegeerp.ui.components.LiveMetricsCard(
                    title = "Subject Performance",
                    metrics = listOf(
                        "Math" to 85f,
                        "Physics" to 92f,
                        "Chemistry" to 78f
                    )
                )
                
                com.example.collegeerp.ui.components.AttendanceChart()
                com.example.collegeerp.ui.components.MarksProgressChart()
                com.example.collegeerp.ui.components.FeesStatusChart()
                
                Text(
                    text = "Quick Actions",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // Student specific cards - 2x2 grid
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ModernQuickAccessCard(
                            title = "Profile",
                            subtitle = "View details",
                            icon = Icons.Default.Person,
                            color = MaterialTheme.colorScheme.primary,
                            onClick = onNavigateToProfile,
                            modifier = Modifier.weight(1f)
                        )
                        ModernQuickAccessCard(
                            title = "Attendance",
                            subtitle = "Track record",
                            icon = Icons.Default.DateRange,
                            color = MaterialTheme.colorScheme.secondary,
                            onClick = onNavigateToAttendance,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ModernQuickAccessCard(
                            title = "Marks",
                            subtitle = "View results",
                            icon = Icons.Default.Star,
                            color = MaterialTheme.colorScheme.tertiary,
                            onClick = onNavigateToMarks,
                            modifier = Modifier.weight(1f)
                        )
                        ModernQuickAccessCard(
                            title = "Fees",
                            subtitle = "Payment info",
                            icon = Icons.Default.AccountBox,
                            color = MaterialTheme.colorScheme.error,
                            onClick = onNavigateToFees,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModernQuickAccessCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            
            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun StudentBottomNavigation(
    onNavigateToProfile: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Attendance"
                )
            },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Marks"
                )
            },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            selected = false,
            onClick = onNavigateToProfile,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}