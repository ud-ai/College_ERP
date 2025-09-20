package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultAnalysisScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Result Analysis") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { ExamPerformanceOverview() }
            item { SubjectWiseAnalysis() }
            item { GradeDistribution() }
            item { PassFailStatistics() }
            item { TopPerformers() }
        }
    }
}

@Composable
fun ExamPerformanceOverview() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Exam Performance Overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PerformanceStat("Total Students", "1,247", Color(0xFF2196F3))
                PerformanceStat("Average Score", "78.5%", Color(0xFF4CAF50))
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PerformanceStat("Pass Rate", "92%", Color(0xFF9C27B0))
                PerformanceStat("Distinction", "23%", Color(0xFFFF9800))
            }
        }
    }
}

@Composable
fun PerformanceStat(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun SubjectWiseAnalysis() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFF44336)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Subject-wise Performance",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            SubjectPerformanceItem("Mathematics", 82.5f, 95)
            SubjectPerformanceItem("Physics", 78.2f, 88)
            SubjectPerformanceItem("Chemistry", 85.1f, 92)
            SubjectPerformanceItem("Computer Science", 89.3f, 97)
            SubjectPerformanceItem("English", 76.8f, 85)
        }
    }
}

@Composable
fun SubjectPerformanceItem(subject: String, avgScore: Float, passRate: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = subject,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Avg: ${avgScore}% • Pass: ${passRate}%",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        PerformanceIndicator(avgScore)
    }
}

@Composable
fun PerformanceIndicator(score: Float) {
    val color = when {
        score >= 85 -> Color(0xFF4CAF50)
        score >= 75 -> Color(0xFFFF9800)
        else -> Color(0xFFFF5722)
    }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Text(
            text = when {
                score >= 85 -> "Excellent"
                score >= 75 -> "Good"
                else -> "Needs Improvement"
            },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GradeDistribution() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Grade Distribution",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GradeStat("A+", "23%", Color(0xFF4CAF50))
                GradeStat("A", "28%", Color(0xFF8BC34A))
                GradeStat("B+", "22%", Color(0xFFFF9800))
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GradeStat("B", "15%", Color(0xFFFFC107))
                GradeStat("C", "8%", Color(0xFFFF5722))
                GradeStat("F", "4%", Color(0xFFF44336))
            }
        }
    }
}

@Composable
fun GradeStat(grade: String, percentage: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = percentage,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = "Grade $grade",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun PassFailStatistics() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pass/Fail Statistics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Total Appeared: 1,247 students")
            Text("Passed: 1,147 students (92%)")
            Text("Failed: 100 students (8%)")
            Text("Improvement from last semester: +5%")
        }
    }
}

@Composable
fun TopPerformers() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Top Performers",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TopPerformerItem("1", "Sarah Johnson", "Computer Science", "96.5%")
            TopPerformerItem("2", "Michael Chen", "Mathematics", "95.8%")
            TopPerformerItem("3", "Emily Davis", "Physics", "94.2%")
            TopPerformerItem("4", "David Wilson", "Chemistry", "93.7%")
            TopPerformerItem("5", "Lisa Anderson", "English", "92.9%")
        }
    }
}

@Composable
fun TopPerformerItem(rank: String, name: String, course: String, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFFD700).copy(alpha = 0.2f),
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = rank,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF8F00)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = course,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        Text(
            text = score,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50)
        )
    }
}