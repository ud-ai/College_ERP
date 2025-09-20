package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MaintenanceRequest(
    val id: String,
    val roomNumber: String,
    val issue: String,
    val priority: String,
    val status: String,
    val reportedDate: String,
    val reportedBy: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostelMaintenanceScreen(
    onBack: () -> Unit
) {
    val maintenanceRequests = remember {
        listOf(
            MaintenanceRequest("M001", "A-101", "Leaking faucet", "High", "In Progress", "2024-01-15", "John Doe"),
            MaintenanceRequest("M002", "B-205", "Broken window", "Medium", "Pending", "2024-01-14", "Jane Smith"),
            MaintenanceRequest("M003", "C-301", "AC not working", "High", "Completed", "2024-01-13", "Mike Johnson"),
            MaintenanceRequest("M004", "A-102", "Door lock issue", "Low", "Pending", "2024-01-12", "Sarah Wilson"),
            MaintenanceRequest("M005", "B-203", "Light not working", "Medium", "In Progress", "2024-01-11", "David Brown")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Maintenance Requests") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Add new request */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Request")
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                MaintenanceSummaryCard(maintenanceRequests)
            }
            
            items(maintenanceRequests) { request ->
                MaintenanceRequestCard(request = request)
            }
        }
    }
}

@Composable
fun MaintenanceSummaryCard(requests: List<MaintenanceRequest>) {
    val pending = requests.count { it.status == "Pending" }
    val inProgress = requests.count { it.status == "In Progress" }
    val completed = requests.count { it.status == "Completed" }

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
                text = "Maintenance Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusSummary("Pending", pending, Color(0xFFFF9800))
                StatusSummary("In Progress", inProgress, Color(0xFF2196F3))
                StatusSummary("Completed", completed, Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
fun StatusSummary(label: String, count: Int, color: Color) {
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
fun MaintenanceRequestCard(request: MaintenanceRequest) {
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
                    Icons.Default.Build,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Room ${request.roomNumber}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = request.issue,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    MaintenanceStatusChip(request.status)
                    Spacer(modifier = Modifier.height(4.dp))
                    PriorityChip(request.priority)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Reported by: ${request.reportedBy}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = request.reportedDate,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun MaintenanceStatusChip(status: String) {
    val color = when (status) {
        "Pending" -> Color(0xFFFF9800)
        "In Progress" -> Color(0xFF2196F3)
        "Completed" -> Color(0xFF4CAF50)
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
fun PriorityChip(priority: String) {
    val color = when (priority) {
        "High" -> Color(0xFFFF5722)
        "Medium" -> Color(0xFFFF9800)
        "Low" -> Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.primary
    }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Text(
            text = priority,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 10.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}