package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StudentDetailScreen(studentId: String) {
    Column(Modifier.padding(16.dp)) {
        Text("Student Detail: $studentId")
        Text("Payments, hostel, exams will appear here")
    }
}


