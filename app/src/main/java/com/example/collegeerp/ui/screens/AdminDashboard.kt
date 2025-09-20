package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    onNavigateToAdmissions: () -> Unit = {},
    onNavigateToStudents: () -> Unit = {},
    onNavigateToPayments: () -> Unit = {},
    onNavigateToHostel: () -> Unit = {},
    onNavigateToExams: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToAnalytics: () -> Unit = {},
    onNavigateToDocuments: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    Scaffold(
        bottomBar = { 
            AdminBottomNavigation(
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToNotifications = onNavigateToNotifications,
                onNavigateToDocuments = onNavigateToDocuments
            ) 
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF673AB7))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Admin Dashboard",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "System Administration",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DepartmentQuickAccessCard(
                            title = "Admissions",
                            icon = Icons.Default.Add,
                            color = Color(0xFF4CAF50),
                            onClick = onNavigateToAdmissions,
                            modifier = Modifier.weight(1f)
                        )
                        DepartmentQuickAccessCard(
                            title = "Students",
                            icon = Icons.Default.Person,
                            color = Color(0xFF2196F3),
                            onClick = onNavigateToStudents,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DepartmentQuickAccessCard(
                            title = "Payments",
                            icon = Icons.Default.AccountBox,
                            color = Color(0xFFFF9800),
                            onClick = onNavigateToPayments,
                            modifier = Modifier.weight(1f)
                        )
                        DepartmentQuickAccessCard(
                            title = "Hostel",
                            icon = Icons.Default.Home,
                            color = Color(0xFF9C27B0),
                            onClick = onNavigateToHostel,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DepartmentQuickAccessCard(
                            title = "Exams",
                            icon = Icons.Default.Star,
                            color = Color(0xFFF44336),
                            onClick = onNavigateToExams,
                            modifier = Modifier.weight(1f)
                        )
                        DepartmentQuickAccessCard(
                            title = "Analytics",
                            icon = Icons.Default.Info,
                            color = Color(0xFF607D8B),
                            onClick = onNavigateToAnalytics,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminBottomNavigation(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToDocuments: () -> Unit = {}
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
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
                selectedIconColor = Color(0xFF673AB7),
                unselectedIconColor = Color(0xFF999999),
                indicatorColor = Color(0xFF673AB7).copy(alpha = 0.12f)
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            },
            selected = false,
            onClick = onNavigateToNotifications,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF673AB7),
                unselectedIconColor = Color(0xFF999999),
                indicatorColor = Color(0xFF673AB7).copy(alpha = 0.12f)
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Documents"
                )
            },
            selected = false,
            onClick = onNavigateToDocuments,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF673AB7),
                unselectedIconColor = Color(0xFF999999),
                indicatorColor = Color(0xFF673AB7).copy(alpha = 0.12f)
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
                selectedIconColor = Color(0xFF673AB7),
                unselectedIconColor = Color(0xFF999999),
                indicatorColor = Color(0xFF673AB7).copy(alpha = 0.12f)
            )
        )
    }
}