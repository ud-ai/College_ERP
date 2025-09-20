package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collegeerp.domain.model.Document
import com.example.collegeerp.domain.model.DocumentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen(
    onBack: () -> Unit
) {
    val documents = remember {
        listOf(
            Document("1", "Aadhar Card", DocumentType.AADHAR, "", "student1", size = 245760),
            Document("2", "10th Marksheet", DocumentType.MARKSHEET, "", "student1", size = 512000),
            Document("3", "Fee Receipt", DocumentType.RECEIPT, "", "admin", size = 128000)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Documents") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Upload document */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Upload")
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(documents) { document ->
                DocumentCard(document = document)
            }
        }
    }
}

@Composable
fun DocumentCard(document: Document) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = getDocumentColor(document.type),
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = document.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "${formatFileSize(document.size)} • ${document.type.name}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            TextButton(onClick = { /* View document */ }) {
                Text("View")
            }
        }
    }
}

@Composable
fun getDocumentColor(type: DocumentType): Color {
    return when (type) {
        DocumentType.PHOTO -> Color(0xFF2196F3)
        DocumentType.AADHAR -> Color(0xFF4CAF50)
        DocumentType.MARKSHEET -> Color(0xFF9C27B0)
        DocumentType.CERTIFICATE -> Color(0xFFFF9800)
        DocumentType.RECEIPT -> Color(0xFFF44336)
        else -> MaterialTheme.colorScheme.primary
    }
}

fun formatFileSize(bytes: Long): String {
    return when {
        bytes >= 1024 * 1024 -> "${bytes / (1024 * 1024)} MB"
        bytes >= 1024 -> "${bytes / 1024} KB"
        else -> "$bytes B"
    }
}