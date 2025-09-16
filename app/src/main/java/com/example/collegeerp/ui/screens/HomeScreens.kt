package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AdminHome() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Admin Home")
    }
}

@Composable
fun StaffHome() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Staff Home")
    }
}

@Composable
fun StudentHome() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Student Home")
    }
}


