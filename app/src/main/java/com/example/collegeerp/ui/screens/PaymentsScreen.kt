package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaymentsScreen(studentId: String, onRecord: (amount: Double, method: String) -> Unit) {
    var amountText by remember { mutableStateOf("") }
    var method by remember { mutableStateOf("CASH") }
    Column(Modifier.padding(16.dp)) {
        Text("Record Payment for $studentId")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = amountText, onValueChange = { amountText = it }, label = { Text("Amount") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = method, onValueChange = { method = it }, label = { Text("Method") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = { amountText.toDoubleOrNull()?.let { onRecord(it, method) } }, enabled = amountText.toDoubleOrNull() != null) {
            Text("Save Payment")
        }
    }
}


