package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collegeerp.ui.PaymentsViewModel
import androidx.compose.foundation.text.KeyboardOptions

data class FeeStructure(
    val feeType: String,
    val amount: String,
    val dueDate: String,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsScreen(
    studentId: String, 
    onRecord: (amount: Double, method: String) -> Unit = { _, _ -> },
    onBack: () -> Unit = {}
) {
    val viewModel: PaymentsViewModel = hiltViewModel()
    var showPaymentDialog by remember { mutableStateOf(false) }
    
    val feeStructure = remember {
        listOf(
            FeeStructure("Tuition Fee", "₹25,000", "2024-03-15", "Pending"),
            FeeStructure("Hostel Fee", "₹15,000", "2024-03-20", "Paid"),
            FeeStructure("Lab Fee", "₹5,000", "2024-03-25", "Pending"),
            FeeStructure("Library Fee", "₹2,000", "2024-03-30", "Paid"),
            FeeStructure("Sports Fee", "₹3,000", "2024-04-05", "Pending")
        )
    }
    
    val paymentHistory = remember {
        listOf(
            PaymentHistory("PAY001", "Hostel Fee", "₹15,000", "2024-01-15", "Online", "Completed"),
            PaymentHistory("PAY002", "Library Fee", "₹2,000", "2024-01-10", "Cash", "Completed"),
            PaymentHistory("PAY003", "Registration Fee", "₹1,000", "2024-01-05", "Online", "Completed")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("My Fees") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { showPaymentDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Make Payment")
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                FeesSummaryCard(feeStructure)
            }
            
            item {
                Text(
                    text = "Fee Structure",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(feeStructure) { fee ->
                FeeStructureCard(fee = fee, onPayNow = { showPaymentDialog = true })
            }
            
            item {
                Text(
                    text = "Payment History",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(paymentHistory) { payment ->
                PaymentHistoryCard(payment = payment)
            }
        }
    }
    
    if (showPaymentDialog) {
        PaymentDialog(
            onDismiss = { showPaymentDialog = false },
            onPayment = { amount, method ->
                viewModel.recordPayment(studentId, amount, method)
                onRecord(amount, method)
                showPaymentDialog = false
            }
        )
    }
}

data class PaymentHistory(
    val id: String,
    val feeType: String,
    val amount: String,
    val date: String,
    val method: String,
    val status: String
)

@Composable
fun FeesSummaryCard(fees: List<FeeStructure>) {
    val totalFees = fees.sumOf { it.amount.replace("₹", "").replace(",", "").toIntOrNull() ?: 0 }
    val paidFees = fees.filter { it.status == "Paid" }.sumOf { it.amount.replace("₹", "").replace(",", "").toIntOrNull() ?: 0 }
    val pendingFees = totalFees - paidFees

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
                text = "Fee Summary",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeeStat("Total Fees", "₹${String.format("%,d", totalFees)}", Color(0xFF2196F3))
                FeeStat("Paid", "₹${String.format("%,d", paidFees)}", Color(0xFF4CAF50))
                FeeStat("Pending", "₹${String.format("%,d", pendingFees)}", Color(0xFFFF9800))
            }
        }
    }
}

@Composable
fun FeeStat(label: String, value: String, color: Color) {
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
fun FeeStructureCard(fee: FeeStructure, onPayNow: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = fee.feeType,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Due: ${fee.dueDate}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = fee.amount,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if (fee.status == "Paid") Color(0xFF4CAF50) else Color(0xFFFF9800)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (fee.status == "Pending") {
                    Button(
                        onClick = onPayNow,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Text("Pay Now", fontSize = 12.sp)
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF4CAF50).copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "Paid",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentHistoryCard(payment: PaymentHistory) {
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
                    tint = Color(0xFF4CAF50)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = payment.feeType,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${payment.method} • ${payment.date}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                
                Text(
                    text = payment.amount,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4CAF50)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Payment ID: ${payment.id}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun PaymentDialog(
    onDismiss: () -> Unit,
    onPayment: (Double, String) -> Unit
) {
    var amountText by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("Online") }
    val paymentMethods = listOf("Online", "Cash", "Cheque", "Card")
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Make Payment") },
        text = {
            Column {
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount (₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = selectedMethod,
                    onValueChange = { selectedMethod = it },
                    label = { Text("Payment Method") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = "Available methods: Online, Cash, Cheque, Card",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    amountText.toDoubleOrNull()?.let { amount ->
                        onPayment(amount, selectedMethod)
                    }
                },
                enabled = amountText.toDoubleOrNull() != null
            ) {
                Text("Pay Now")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


