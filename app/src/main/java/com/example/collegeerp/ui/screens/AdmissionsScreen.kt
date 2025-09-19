package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collegeerp.ui.AdmissionsViewModel

@Composable
fun AdmissionsScreen(onSubmit: (fullName: String, program: String) -> Unit) {
    val viewModel: AdmissionsViewModel = hiltViewModel()
    var name by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var guardianName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    
    val photoData: ByteArray? = null // TODO: add camera/gallery picker
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Student Admission Form",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name *") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = name.isBlank() && errorMessage != null
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = program,
            onValueChange = { program = it },
            label = { Text("Program *") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("e.g., BSc Computer Science") },
            isError = program.isBlank() && errorMessage != null
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Contact Phone") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = guardianName,
            onValueChange = { guardianName = it },
            label = { Text("Guardian Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Error message
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        // Success message
        successMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        Button(
            onClick = {
                if (name.isBlank() || program.isBlank()) {
                    errorMessage = "Please fill in all required fields"
                    return@Button
                }
                
                isLoading = true
                errorMessage = null
                
                try {
                    viewModel.submitAdmission(name, program, photoData)
                    onSubmit(name, program)
                    successMessage = "Admission submitted successfully!"
                    // Clear form
                    name = ""
                    program = ""
                    phone = ""
                    guardianName = ""
                } catch (e: Exception) {
                    errorMessage = "Failed to submit admission: ${e.message}"
                } finally {
                    isLoading = false
                }
            },
            enabled = !isLoading && name.isNotBlank() && program.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("Submit Admission")
        }
    }
}


