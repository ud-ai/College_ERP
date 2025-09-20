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
fun AccountsStaffDashboard(
    onNavigateToPayments: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    Scaffold(
        bottomBar = { 
            AccountsBottomNavigation(
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToPayments = onNavigateToPayments,
                onSignOut = onSignOut
            ) 
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF9800))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    
                    Text(
                        text = "Accounts Department",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Financial Management",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                
                // Accounts specific cards
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DepartmentQuickAccessCard(
                            title = "Fee Collection",
                            icon = Icons.Default.AccountBox,
                            color = Color(0xFFFF9800),
                            onClick = onNavigateToPayments,
                            modifier = Modifier.weight(1f)
                        )
                        DepartmentQuickAccessCard(
                            title = "Payment Records",
                            icon = Icons.Default.List,
                            color = Color(0xFF2196F3),
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DepartmentQuickAccessCard(
                            title = "Financial Reports",
                            icon = Icons.Default.Info,
                            color = Color(0xFF4CAF50),
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        )
                        DepartmentQuickAccessCard(
                            title = "Outstanding Dues",
                            icon = Icons.Default.Warning,
                            color = Color(0xFFF44336),
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AccountsBottomNavigation(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToPayments: () -> Unit = {},
    onSignOut: () -> Unit = {}
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
                selectedIconColor = Color(0xFFFF9800),
                unselectedIconColor = Color(0xFF999999),
                indicatorColor = Color(0xFFFF9800).copy(alpha = 0.12f)
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.AccountBox,
                    contentDescription = "Payments"
                )
            },
            selected = false,
            onClick = onNavigateToPayments,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFFF9800),
                unselectedIconColor = Color(0xFF999999),
                indicatorColor = Color(0xFFFF9800).copy(alpha = 0.12f)
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
                selectedIconColor = Color(0xFFFF9800),
                unselectedIconColor = Color(0xFF999999),
                indicatorColor = Color(0xFFFF9800).copy(alpha = 0.12f)
            )
        )
    }
}