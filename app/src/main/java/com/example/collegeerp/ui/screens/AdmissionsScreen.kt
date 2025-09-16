package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdmissionsScreen(onSubmit: (fullName: String, program: String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        Text("Admissions")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full name") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = program, onValueChange = { program = it }, label = { Text("Program") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = { onSubmit(name, program) }, enabled = name.isNotBlank() && program.isNotBlank()) {
            Text("Submit")
        }
    }
}


