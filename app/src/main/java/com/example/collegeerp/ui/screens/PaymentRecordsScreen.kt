package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PaymentRecord(
    val id: String,
    val studentName: String,
    val studentId: String,
    val amount: String,
    val paymentDate: String,
    val paymentMethod: String,
    val feeType: String,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentRecordsScreen(
    onBack: () -> Unit
) {
    val paymentRecords = remember {
        listOf(
            PaymentRecord("PAY001", "John Doe", "STU001", "₹25,000", "2024-01-15", "Online", "Tuition Fee", "Completed"),
            PaymentRecord("PAY002", "Jane Smith", "STU002", "₹15,000", "2024-01-14", "Cash", "Hostel Fee", "Completed"),
            PaymentRecord("PAY003", "Mike Johnson", "STU003", "₹30,000", "2024-01-13", "Cheque", "Tuition Fee", "Pending"),
            PaymentRecord("PAY004", "Sarah Wilson", "STU004", "₹12,000", "2024-01-12", "Online", "Lab Fee", "Completed"),
            PaymentRecord("PAY005", "David Brown", "STU005", "₹20,000", "2024-01-11", "Online", "Tuition Fee", "Failed")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Payment Records") },
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
                PaymentSummaryCard(paymentRecords)
            }
            
            items(paymentRecords) { record ->
                PaymentRecordCard(record = record)
            }
        }
    }
}

@Composable
fun PaymentSummaryCard(records: List<PaymentRecord>) {
    val totalAmount = records.filter { it.status == "Completed" }
        .sumOf { it.amount.replace("₹", "").replace(",", "").toIntOrNull() ?: 0 }
    val completedCount = records.count { it.status == "Completed" }
    val pendingCount = records.count { it.status == "Pending" }

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
                text = "Payment Summary",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PaymentStat("Total Collected", "₹${String.format("%,d", totalAmount)}", Color(0xFF4CAF50))
                PaymentStat("Completed", completedCount.toString(), Color(0xFF2196F3))
                PaymentStat("Pending", pendingCount.toString(), Color(0xFFFF9800))
            }
        }
    }
}

@Composable
fun PaymentStat(label: String, value: String, color: Color) {
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
fun PaymentRecordCard(record: PaymentRecord) {
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
                    Icons.Default.AccountBox,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = record.studentName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${record.feeType} • ${record.studentId}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = record.amount,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF4CAF50)
                    )
                    PaymentStatusChip(record.status)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Payment ID: ${record.id}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "${record.paymentMethod} • ${record.paymentDate}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun PaymentStatusChip(status: String) {
    val color = when (status) {
        "Completed" -> Color(0xFF4CAF50)
        "Pending" -> Color(0xFFFF9800)
        "Failed" -> Color(0xFFFF5722)
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