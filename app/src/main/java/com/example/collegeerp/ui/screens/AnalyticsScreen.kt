package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Analytics Dashboard",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Student Enrollment Trends
            StudentEnrollmentChart()
            
            // Revenue Analytics
            RevenueChart()
            
            // Department Performance
            DepartmentPerformanceChart()
            
            // Monthly Activity
            MonthlyActivityChart()
        }
    }
}

@Composable
fun StudentEnrollmentChart() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Student Enrollment Trends",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Monthly enrollment over the past year",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            AndroidView(
                factory = { context ->
                    LineChart(context).apply {
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(false)
                        
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            granularity = 1f
                            valueFormatter = IndexAxisValueFormatter(
                                listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                                      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                            )
                        }
                        
                        axisLeft.apply {
                            setDrawGridLines(true)
                            axisMinimum = 0f
                        }
                        
                        axisRight.isEnabled = false
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val enrollmentData = listOf(45f, 52f, 38f, 67f, 89f, 95f, 78f, 85f, 92f, 88f, 76f, 82f)
                    val entries = enrollmentData.mapIndexed { index, value ->
                        Entry(index.toFloat(), value)
                    }
                    
                    val dataSet = LineDataSet(entries, "Enrollments").apply {
                        color = android.graphics.Color.parseColor("#6366F1")
                        setCircleColor(android.graphics.Color.parseColor("#6366F1"))
                        lineWidth = 3f
                        circleRadius = 5f
                        setDrawCircleHole(false)
                        valueTextSize = 10f
                        setDrawFilled(true)
                        fillColor = android.graphics.Color.parseColor("#6366F1")
                    }
                    
                    chart.data = LineData(dataSet)
                    chart.invalidate()
                },
                modifier = Modifier.height(250.dp)
            )
        }
    }
}

@Composable
fun RevenueChart() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Revenue Analytics",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Monthly revenue breakdown",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            AndroidView(
                factory = { context ->
                    BarChart(context).apply {
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(false)
                        setDrawBarShadow(false)
                        setDrawValueAboveBar(true)
                        
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            granularity = 1f
                            valueFormatter = IndexAxisValueFormatter(
                                listOf("Tuition", "Hostel", "Library", "Lab", "Sports", "Other")
                            )
                        }
                        
                        axisLeft.apply {
                            setDrawGridLines(true)
                            axisMinimum = 0f
                        }
                        
                        axisRight.isEnabled = false
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val revenueData = listOf(450000f, 180000f, 25000f, 75000f, 30000f, 45000f)
                    val entries = revenueData.mapIndexed { index, value ->
                        BarEntry(index.toFloat(), value)
                    }
                    
                    val dataSet = BarDataSet(entries, "Revenue").apply {
                        color = android.graphics.Color.parseColor("#10B981")
                        valueTextSize = 10f
                    }
                    
                    chart.data = BarData(dataSet)
                    chart.invalidate()
                },
                modifier = Modifier.height(250.dp)
            )
        }
    }
}

@Composable
fun DepartmentPerformanceChart() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Department Performance",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Efficiency ratings across departments",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            AndroidView(
                factory = { context ->
                    RadarChart(context).apply {
                        description.isEnabled = false
                        webLineWidth = 1f
                        webColor = android.graphics.Color.LTGRAY
                        webLineWidthInner = 1f
                        webColorInner = android.graphics.Color.LTGRAY
                        webAlpha = 100
                        
                        yAxis.apply {
                            setLabelCount(5, false)
                            textSize = 9f
                            axisMinimum = 0f
                            axisMaximum = 100f
                            setDrawLabels(false)
                        }
                        
                        xAxis.apply {
                            textSize = 11f
                            yOffset = 0f
                            xOffset = 0f
                            valueFormatter = IndexAxisValueFormatter(
                                listOf("Admissions", "Academic", "Finance", "Hostel", "Exams")
                            )
                            textColor = android.graphics.Color.BLACK
                        }
                        
                        legend.isEnabled = false
                        setTouchEnabled(false)
                    }
                },
                update = { chart ->
                    val performanceData = listOf(85f, 92f, 78f, 88f, 95f)
                    val entries = performanceData.map { value ->
                        RadarEntry(value)
                    }
                    
                    val dataSet = RadarDataSet(entries, "Performance").apply {
                        color = android.graphics.Color.parseColor("#F59E0B")
                        fillColor = android.graphics.Color.parseColor("#F59E0B")
                        setDrawFilled(true)
                        fillAlpha = 180
                        lineWidth = 2f
                        isDrawHighlightCircleEnabled = true
                        setDrawHighlightIndicators(false)
                    }
                    
                    chart.data = RadarData(dataSet)
                    chart.invalidate()
                },
                modifier = Modifier.height(300.dp)
            )
        }
    }
}

@Composable
fun MonthlyActivityChart() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Monthly Activity Overview",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "System usage and activity metrics",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            AndroidView(
                factory = { context ->
                    CombinedChart(context).apply {
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(false)
                        setDrawGridBackground(false)
                        
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            granularity = 1f
                            valueFormatter = IndexAxisValueFormatter(
                                listOf("Week 1", "Week 2", "Week 3", "Week 4")
                            )
                        }
                        
                        axisLeft.apply {
                            setDrawGridLines(true)
                            axisMinimum = 0f
                        }
                        
                        axisRight.isEnabled = false
                        legend.isEnabled = true
                    }
                },
                update = { chart ->
                    val loginData = listOf(120f, 145f, 132f, 158f)
                    val transactionData = listOf(45f, 52f, 38f, 67f)
                    
                    val loginEntries = loginData.mapIndexed { index, value ->
                        BarEntry(index.toFloat(), value)
                    }
                    
                    val transactionEntries = transactionData.mapIndexed { index, value ->
                        Entry(index.toFloat(), value)
                    }
                    
                    val barDataSet = BarDataSet(loginEntries, "Logins").apply {
                        color = android.graphics.Color.parseColor("#6366F1")
                    }
                    
                    val lineDataSet = LineDataSet(transactionEntries, "Transactions").apply {
                        color = android.graphics.Color.parseColor("#10B981")
                        setCircleColor(android.graphics.Color.parseColor("#10B981"))
                        lineWidth = 3f
                        circleRadius = 5f
                        setDrawCircleHole(false)
                    }
                    
                    val combinedData = CombinedData().apply {
                        setData(BarData(barDataSet))
                        setData(LineData(lineDataSet))
                    }
                    
                    chart.data = combinedData
                    chart.invalidate()
                },
                modifier = Modifier.height(250.dp)
            )
        }
    }
}