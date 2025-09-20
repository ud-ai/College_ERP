package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TranscriptRequest(
    val id: String,
    val studentName: String,
    val studentId: String,
    val course: String,
    val graduationYear: String,
    val requestDate: String,
    val status: String,
    val requestType: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranscriptsScreen(
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    val transcriptRequests = remember {
        listOf(
            TranscriptRequest("TR001", "John Doe", "STU001", "Computer Science", "2023", "2024-01-15", "Completed", "Official"),
            TranscriptRequest("TR002", "Jane Smith", "STU002", "Mathematics", "2022", "2024-01-14", "In Progress", "Unofficial"),
            TranscriptRequest("TR003", "Mike Johnson", "STU003", "Physics", "2023", "2024-01-13", "Pending", "Official"),
            TranscriptRequest("TR004", "Sarah Wilson", "STU004", "Chemistry", "2021", "2024-01-12", "Completed", "Official"),
            TranscriptRequest("TR005", "David Brown", "STU005", "English", "2022", "2024-01-11", "In Progress", "Unofficial")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Transcripts") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by student name or ID") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                TranscriptSummaryCard(transcriptRequests)
            }
            
            items(transcriptRequests.filter { 
                it.studentName.contains(searchQuery, ignoreCase = true) || 
                it.studentId.contains(searchQuery, ignoreCase = true)
            }) { request ->
                TranscriptRequestCard(
                    request = request,
                    onDownload = { /* Download transcript */ },
                    onGenerate = { /* Generate transcript */ }
                )
            }
        }
    }
}

@Composable
fun TranscriptSummaryCard(requests: List<TranscriptRequest>) {
    val completed = requests.count { it.status == "Completed" }
    val inProgress = requests.count { it.status == "In Progress" }
    val pending = requests.count { it.status == "Pending" }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Transcript Requests Summary",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TranscriptStat("Completed", completed, Color(0xFF4CAF50))
                TranscriptStat("In Progress", inProgress, Color(0xFF2196F3))
                TranscriptStat("Pending", pending, Color(0xFFFF9800))
            }
        }
    }
}

@Composable
fun TranscriptStat(label: String, count: Int, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun TranscriptRequestCard(
    request: TranscriptRequest,
    onDownload: () -> Unit,
    onGenerate: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.List,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = request.studentName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${request.course} • ${request.studentId}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    TranscriptStatusChip(request.status)
                    Spacer(modifier = Modifier.height(4.dp))
                    TranscriptTypeChip(request.requestType)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Graduation Year: ${request.graduationYear}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Requested: ${request.requestDate}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (request.status == "Completed") {
                        OutlinedButton(
                            onClick = onDownload,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Download", fontSize = 12.sp)
                        }
                    } else {
                        Button(
                            onClick = onGenerate,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF44336)
                            )
                        ) {
                            Text("Generate", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TranscriptStatusChip(status: String) {
    val color = when (status) {
        "Completed" -> Color(0xFF4CAF50)
        "In Progress" -> Color(0xFF2196F3)
        "Pending" -> Color(0xFFFF9800)
        else -> MaterialTheme.colorScheme.primary
    }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TranscriptTypeChip(type: String) {
    val color = if (type == "Official") Color(0xFFF44336) else Color(0xFF9C27B0)
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Text(
            text = type,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 10.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}