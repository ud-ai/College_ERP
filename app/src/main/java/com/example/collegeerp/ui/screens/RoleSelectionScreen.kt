package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collegeerp.domain.model.UserRole
import com.example.collegeerp.ui.AuthViewModel

@Composable
fun RoleSelectionScreen(
    onRoleSelected: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var selectedRole by remember { mutableStateOf(UserRole.STUDENT) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select Your Role",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        UserRole.values().forEach { role ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedRole == role,
                    onClick = { selectedRole = role }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = role.name.replace("_", " "),
                    fontSize = 16.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                // Update user role in database
                onRoleSelected()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm Role")
        }
    }
}