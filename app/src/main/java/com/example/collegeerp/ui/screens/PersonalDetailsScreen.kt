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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDetailsScreen(
    onBack: () -> Unit = {},
    onNextStep: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var permanentAddress by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2C2C2C)
                )
            }
            
            Text(
                text = "Personal Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C2C2C),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        // Progress Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Step 1 - Active
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        Color(0xFF2196F3),
                        RoundedCornerShape(2.dp)
                    )
            )
            // Step 2 - Inactive
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        Color(0xFFE0E0E0),
                        RoundedCornerShape(2.dp)
                    )
            )
            // Step 3 - Inactive
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        Color(0xFFE0E0E0),
                        RoundedCornerShape(2.dp)
                    )
            )
            // Step 4 - Inactive
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        Color(0xFFE0E0E0),
                        RoundedCornerShape(2.dp)
                    )
            )
        }
        
        // Form Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Full Name
            Text(
                text = "Full Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2C)
            )
            
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { 
                    Text(
                        "Enter your full name",
                        color = Color(0xFF9E9E9E)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            // Date of Birth and Gender Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Date of Birth",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2C2C2C)
                    )
                    
                    OutlinedTextField(
                        value = dateOfBirth,
                        onValueChange = { dateOfBirth = it },
                        placeholder = { 
                            Text(
                                "DD/MM/YYYY",
                                color = Color(0xFF9E9E9E)
                            ) 
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select Date",
                                tint = Color(0xFF666666)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE0E0E0),
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Gender",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2C2C2C)
                    )
                    
                    var expanded by remember { mutableStateOf(false) }
                    val genders = listOf("Male", "Female", "Other")
                    
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selectedGender,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { 
                                Text(
                                    "Select",
                                    color = Color(0xFF9E9E9E)
                                ) 
                            },
                            trailingIcon = { 
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) 
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE0E0E0),
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            genders.forEach { gender ->
                                DropdownMenuItem(
                                    text = { Text(gender) },
                                    onClick = {
                                        selectedGender = gender
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            
            // Contact Number
            Text(
                text = "Contact Number",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2C)
            )
            
            OutlinedTextField(
                value = contactNumber,
                onValueChange = { contactNumber = it },
                placeholder = { 
                    Text(
                        "Enter your contact number",
                        color = Color(0xFF9E9E9E)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            // Email Address
            Text(
                text = "Email Address",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2C)
            )
            
            OutlinedTextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                placeholder = { 
                    Text(
                        "Enter your email address",
                        color = Color(0xFF9E9E9E)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            // Permanent Address
            Text(
                text = "Permanent Address",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2C)
            )
            
            OutlinedTextField(
                value = permanentAddress,
                onValueChange = { permanentAddress = it },
                placeholder = { 
                    Text(
                        "Enter your permanent address",
                        color = Color(0xFF9E9E9E)
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Next Step Button
            Button(
                onClick = onNextStep,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = fullName.isNotBlank() && emailAddress.isNotBlank()
            ) {
                Text(
                    text = "Next Step",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}