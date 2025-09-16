package com.example.collegeerp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collegeerp.domain.model.Student

@Composable
fun StudentsScreen(students: List<Student>, onOpen: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Search") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        val filtered = students.filter { it.fullName.contains(query, ignoreCase = true) }
        LazyColumn(Modifier.fillMaxSize()) {
            items(filtered) { s ->
                Row(Modifier.fillMaxWidth().clickable { onOpen(s.studentId) }.padding(8.dp)) {
                    Text(s.fullName)
                }
            }
        }
    }
}


