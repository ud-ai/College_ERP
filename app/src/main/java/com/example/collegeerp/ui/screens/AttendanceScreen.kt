package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AttendanceRecord(
    val subject: String,
    val date: String,
    val status: String,
    val totalClasses: Int,
    val attendedClasses: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    onBack: () -> Unit
) {
    val attendanceRecords = remember {
        listOf(
            AttendanceRecord("Mathematics", "2024-01-15", "Present", 45, 42),
            AttendanceRecord("Physics", "2024-01-15", "Present", 40, 38),
            AttendanceRecord("Chemistry", "2024-01-15", "Absent", 42, 35),
            AttendanceRecord("English", "2024-01-15", "Present", 38, 36),
            AttendanceRecord("Computer Science", "2024-01-15", "Present", 50, 47)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Attendance") },
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
                AttendanceSummaryCard(attendanceRecords)
            }
            
            items(attendanceRecords) { record ->
                AttendanceCard(record = record)
            }
        }
    }
}

@Composable
fun AttendanceSummaryCard(records: List<AttendanceRecord>) {
    val totalClasses = records.sumOf { it.totalClasses }
    val attendedClasses = records.sumOf { it.attendedClasses }
    val percentage = if (totalClasses > 0) (attendedClasses * 100) / totalClasses else 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Overall Attendance",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "$percentage%",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = if (percentage >= 75) Color(0xFF4CAF50) else Color(0xFFFF5722)
            )
            
            Text(
                text = "$attendedClasses / $totalClasses classes",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun AttendanceCard(record: AttendanceRecord) {
    val percentage = if (record.totalClasses > 0) 
        (record.attendedClasses * 100) / record.totalClasses else 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (record.status == "Present") Icons.Default.CheckCircle else Icons.Default.Close,
                contentDescription = record.status,
                tint = if (record.status == "Present") Color(0xFF4CAF50) else Color(0xFFFF5722),
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.subject,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "${record.attendedClasses}/${record.totalClasses} classes",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            Text(
                text = "$percentage%",
                fontWeight = FontWeight.Bold,
                color = if (percentage >= 75) Color(0xFF4CAF50) else Color(0xFFFF5722)
            )
        }
    }
}