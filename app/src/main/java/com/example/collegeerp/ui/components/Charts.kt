package com.example.collegeerp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun AttendanceChart(
    attendanceData: List<Float> = listOf(85f, 92f, 78f, 88f, 95f),
    subjects: List<String> = listOf("Math", "Physics", "Chemistry", "Biology", "English"),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Attendance Overview",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
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
                            valueFormatter = IndexAxisValueFormatter(subjects)
                        }
                        
                        axisLeft.apply {
                            setDrawGridLines(true)
                            axisMinimum = 0f
                            axisMaximum = 100f
                        }
                        
                        axisRight.isEnabled = false
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val entries = attendanceData.mapIndexed { index, value ->
                        BarEntry(index.toFloat(), value)
                    }
                    
                    val dataSet = BarDataSet(entries, "Attendance").apply {
                        color = android.graphics.Color.parseColor("#6366F1")
                        valueTextSize = 10f
                    }
                    
                    chart.data = BarData(dataSet)
                    chart.invalidate()
                },
                modifier = Modifier.height(200.dp)
            )
        }
    }
}

@Composable
fun MarksProgressChart(
    marksData: List<Float> = listOf(78f, 85f, 92f, 88f, 95f),
    subjects: List<String> = listOf("Math", "Physics", "Chemistry", "Biology", "English"),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Academic Performance",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
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
                            valueFormatter = IndexAxisValueFormatter(subjects)
                        }
                        
                        axisLeft.apply {
                            setDrawGridLines(true)
                            axisMinimum = 0f
                            axisMaximum = 100f
                        }
                        
                        axisRight.isEnabled = false
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val entries = marksData.mapIndexed { index, value ->
                        Entry(index.toFloat(), value)
                    }
                    
                    val dataSet = LineDataSet(entries, "Marks").apply {
                        color = android.graphics.Color.parseColor("#10B981")
                        setCircleColor(android.graphics.Color.parseColor("#10B981"))
                        lineWidth = 3f
                        circleRadius = 5f
                        setDrawCircleHole(false)
                        valueTextSize = 10f
                    }
                    
                    chart.data = LineData(dataSet)
                    chart.invalidate()
                },
                modifier = Modifier.height(200.dp)
            )
        }
    }
}

@Composable
fun DepartmentStatsChart(
    departmentData: List<Pair<String, Float>> = listOf(
        "Admissions" to 45f,
        "Students" to 1250f,
        "Payments" to 85f,
        "Hostel" to 78f,
        "Exams" to 32f
    ),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Department Overview",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            AndroidView(
                factory = { context ->
                    PieChart(context).apply {
                        description.isEnabled = false
                        setUsePercentValues(false)
                        setEntryLabelTextSize(10f)
                        setEntryLabelColor(android.graphics.Color.BLACK)
                        centerText = "Departments"
                        setCenterTextSize(14f)
                        setDrawHoleEnabled(true)
                        setHoleColor(android.graphics.Color.TRANSPARENT)
                        setTransparentCircleColor(android.graphics.Color.WHITE)
                        setTransparentCircleAlpha(110)
                        holeRadius = 40f
                        transparentCircleRadius = 45f
                        setRotationAngle(0f)
                        isRotationEnabled = true
                        isHighlightPerTapEnabled = true
                        legend.isEnabled = false
                    }
                },
                update = { chart ->
                    val entries = departmentData.map { (label, value) ->
                        PieEntry(value, label)
                    }
                    
                    val colors = listOf(
                        android.graphics.Color.parseColor("#6366F1"),
                        android.graphics.Color.parseColor("#10B981"),
                        android.graphics.Color.parseColor("#F59E0B"),
                        android.graphics.Color.parseColor("#EF4444"),
                        android.graphics.Color.parseColor("#6B7280")
                    )
                    
                    val dataSet = PieDataSet(entries, "").apply {
                        setColors(colors)
                        valueTextSize = 11f
                        valueTextColor = android.graphics.Color.WHITE
                    }
                    
                    chart.data = PieData(dataSet)
                    chart.invalidate()
                },
                modifier = Modifier.height(250.dp)
            )
        }
    }
}

@Composable
fun FeesStatusChart(
    paidAmount: Float = 75000f,
    totalAmount: Float = 100000f,
    modifier: Modifier = Modifier
) {
    val percentage = (paidAmount / totalAmount * 100).toInt()
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Fee Payment Status",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "₹${paidAmount.toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Paid",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Box(
                    modifier = Modifier.size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { paidAmount / totalAmount },
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 8.dp,
                        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                    Text(
                        text = "$percentage%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Column {
                    Text(
                        text = "₹${(totalAmount - paidAmount).toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Pending",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun HostelOccupancyChart(
    occupiedRooms: Int = 180,
    totalRooms: Int = 250,
    modifier: Modifier = Modifier
) {
    val occupancyRate = (occupiedRooms.toFloat() / totalRooms * 100).toInt()
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Hostel Occupancy",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = { occupiedRooms.toFloat() / totalRooms },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = MaterialTheme.colorScheme.tertiary,
                trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$occupiedRooms occupied",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$occupancyRate% full",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "${totalRooms - occupiedRooms} available",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}