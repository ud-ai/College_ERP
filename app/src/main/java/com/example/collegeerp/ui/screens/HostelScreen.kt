package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collegeerp.ui.HostelViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button

@Composable
fun HostelScreen() {
    val viewModel: HostelViewModel = hiltViewModel()
    val hostelId = remember { mutableStateOf("") }
    val roomId = remember { mutableStateOf("") }
    val bedNo = remember { mutableStateOf("") }
    val studentId = remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        Text("Hostel Management")
        OutlinedTextField(value = hostelId.value, onValueChange = { hostelId.value = it }, label = { Text("Hostel ID") })
        OutlinedTextField(value = roomId.value, onValueChange = { roomId.value = it }, label = { Text("Room ID") })
        OutlinedTextField(value = bedNo.value, onValueChange = { bedNo.value = it }, label = { Text("Bed No") })
        OutlinedTextField(value = studentId.value, onValueChange = { studentId.value = it }, label = { Text("Student ID") })
        Button(onClick = { if (hostelId.value.isNotBlank() && roomId.value.isNotBlank() && bedNo.value.isNotBlank() && studentId.value.isNotBlank()) viewModel.assignStudent(hostelId.value, roomId.value, bedNo.value, studentId.value) }) { Text("Assign") }
        Button(onClick = { if (hostelId.value.isNotBlank() && roomId.value.isNotBlank() && studentId.value.isNotBlank()) viewModel.unassignStudent(hostelId.value, roomId.value, studentId.value) }) { Text("Unassign") }
    }
}


