package com.example.collegeerp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collegeerp.ui.DashboardViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DashboardScreen() {
    val viewModel: DashboardViewModel = hiltViewModel()
    val kpis = viewModel.kpis.collectAsState().value
    Column(Modifier.padding(16.dp)) {
        Text("Admin Dashboard")
        Text("Today's Collection: ${kpis.todayCollection}")
        Text("This Month: ${kpis.monthCollection}")
        Text("Occupancy: ${kpis.occupancyPercent}%")
        Text("Pending Admissions: ${kpis.pendingAdmissions}")

        AndroidView(factory = { ctx ->
            BarChart(ctx).apply {
                description = Description().apply { text = "Collections" }
            }
        }, update = { chart ->
            val entries = listOf(
                BarEntry(0f, kpis.todayCollection.toFloat()),
                BarEntry(1f, kpis.monthCollection.toFloat())
            )
            val dataSet = BarDataSet(entries, "Fees").apply { valueTextSize = 12f }
            chart.data = BarData(dataSet)
            chart.invalidate()
        })
    }
}


