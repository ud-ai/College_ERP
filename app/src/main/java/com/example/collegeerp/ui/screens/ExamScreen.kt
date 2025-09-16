package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collegeerp.ui.ExamViewModel

@Composable
fun ExamScreen() {
    val viewModel: ExamViewModel = hiltViewModel()
    var examId by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        Text("Exam Records")
        Spacer(Modifier.padding(4.dp))
        OutlinedTextField(value = examId, onValueChange = { examId = it }, label = { Text("Exam ID") })
        OutlinedTextField(value = studentId, onValueChange = { studentId = it }, label = { Text("Student ID") })
        OutlinedTextField(value = grade, onValueChange = { grade = it }, label = { Text("Grade") })
        OutlinedTextField(value = remarks, onValueChange = { remarks = it }, label = { Text("Remarks") })
        Spacer(Modifier.padding(8.dp))
        Button(onClick = { if (examId.isNotBlank() && studentId.isNotBlank()) viewModel.upsertRecord(examId, studentId, grade, remarks.ifBlank { null }) }) {
            Text("Save Record")
        }
    }
}


