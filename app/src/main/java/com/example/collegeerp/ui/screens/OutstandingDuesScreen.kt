package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class OutstandingDue(
    val studentId: String,
    val studentName: String,
    val course: String,
    val totalDue: String,
    val dueDate: String,
    val daysPastDue: Int,
    val feeType: String,
    val contactNumber: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutstandingDuesScreen(
    onBack: () -> Unit
) {
    val outstandingDues = remember {
        listOf(
            OutstandingDue("STU001", "John Doe", "Computer Science", "₹25,000", "2024-01-10", 15, "Tuition Fee", "+91 9876543210"),
            OutstandingDue("STU002", "Jane Smith", "Mathematics", "₹15,000", "2024-01-05", 20, "Hostel Fee", "+91 9876543211"),
            OutstandingDue("STU003", "Mike Johnson", "Physics", "₹30,000", "2023-12-20", 35, "Tuition Fee", "+91 9876543212"),
            OutstandingDue("STU004", "Sarah Wilson", "Chemistry", "₹12,000", "2024-01-12", 13, "Lab Fee", "+91 9876543213"),
            OutstandingDue("STU005", "David Brown", "English", "₹20,000", "2023-12-15", 40, "Tuition Fee", "+91 9876543214")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Outstanding Dues") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutstandingSummaryCard(outstandingDues)
            }
            
            items(outstandingDues) { due ->
                OutstandingDueCard(
                    due = due,
                    onSendReminder = { /* Send reminder */ }
                )
            }
        }
    }
}

@Composable
fun OutstandingSummaryCard(dues: List<OutstandingDue>) {
    val totalOutstanding = dues.sumOf { 
        it.totalDue.replace("₹", "").replace(",", "").toIntOrNull() ?: 0 
    }
    val criticalCount = dues.count { it.daysPastDue > 30 }
    val overdueCount = dues.count { it.daysPastDue > 0 }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF5722).copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Outstanding Summary",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutstandingStat("Total Outstanding", "₹${String.format("%,d", totalOutstanding)}", Color(0xFFFF5722))
                OutstandingStat("Overdue Students", overdueCount.toString(), Color(0xFFFF9800))
                OutstandingStat("Critical (30+ days)", criticalCount.toString(), Color(0xFFF44336))
            }
        }
    }
}

@Composable
fun OutstandingStat(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
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
fun OutstandingDueCard(
    due: OutstandingDue,
    onSendReminder: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (due.daysPastDue > 30) 
                Color(0xFFFF5722).copy(alpha = 0.05f) 
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (due.daysPastDue > 30) Color(0xFFFF5722) else Color(0xFFFF9800)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = due.studentName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${due.course} • ${due.studentId}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = due.totalDue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFFFF5722)
                    )
                    DuePriorityChip(due.daysPastDue)
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
                        text = "${due.feeType} • Due: ${due.dueDate}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = due.contactNumber,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                OutlinedButton(
                    onClick = onSendReminder,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF2196F3)
                    )
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Send Reminder", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun DuePriorityChip(daysPastDue: Int) {
    val (text, color) = when {
        daysPastDue > 30 -> "Critical" to Color(0xFFFF5722)
        daysPastDue > 15 -> "High" to Color(0xFFFF9800)
        daysPastDue > 0 -> "Medium" to Color(0xFFFFC107)
        else -> "Due Soon" to Color(0xFF4CAF50)
    }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Text(
            text = "$text ($daysPastDue days)",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 10.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}