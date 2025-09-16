package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collegeerp.ui.navigation.Routes

@Composable
fun AdminHome(onNavigate: (String) -> Unit = {}, onSignOut: () -> Unit = {}) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Admin Home")
        Button(onClick = { onNavigate(Routes.DASHBOARD) }, modifier = Modifier.padding(top = 8.dp)) { Text("Dashboard") }
        Button(onClick = { onNavigate(Routes.ADMISSIONS) }, modifier = Modifier.padding(top = 8.dp)) { Text("Admissions") }
        Button(onClick = { onNavigate(Routes.STUDENTS) }, modifier = Modifier.padding(top = 8.dp)) { Text("Students") }
        Button(onClick = { onNavigate(Routes.HOSTEL) }, modifier = Modifier.padding(top = 8.dp)) { Text("Hostel") }
        Button(onClick = { onNavigate(Routes.EXAMS) }, modifier = Modifier.padding(top = 8.dp)) { Text("Exams") }
        Button(onClick = { onSignOut() }, modifier = Modifier.padding(top = 16.dp)) { Text("Sign out") }
    }
}

@Composable
fun StaffHome(onNavigate: (String) -> Unit = {}, onSignOut: () -> Unit = {}) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Staff Home")
        Button(onClick = { onNavigate(Routes.ADMISSIONS) }, modifier = Modifier.padding(top = 8.dp)) { Text("Admissions") }
        Button(onClick = { onNavigate(Routes.STUDENTS) }, modifier = Modifier.padding(top = 8.dp)) { Text("Students") }
        Button(onClick = { onNavigate(Routes.HOSTEL) }, modifier = Modifier.padding(top = 8.dp)) { Text("Hostel") }
        Button(onClick = { onNavigate(Routes.EXAMS) }, modifier = Modifier.padding(top = 8.dp)) { Text("Exams") }
        Button(onClick = { onSignOut() }, modifier = Modifier.padding(top = 16.dp)) { Text("Sign out") }
    }
}

@Composable
fun StudentHome() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Student Home") }
}


