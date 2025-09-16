package com.example.collegeerp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeerp.data.local.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class DashboardKpis(
    val todayCollection: Double,
    val monthCollection: Double,
    val occupancyPercent: Int,
    val pendingAdmissions: Int
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    db: AppDatabase
) : ViewModel() {

    private val payments = db.paymentDao().observeAll()
    private val rooms = db.roomDao().observeAllRooms()
    private val occupancy = db.roomDao().observeAllOccupancy()
    private val students = db.studentDao().observeAll()

    val kpis: StateFlow<DashboardKpis> = combine(
        payments, rooms, occupancy, students
    ) { paymentsList, roomList, occupancyList, studentsList ->
        val now = System.currentTimeMillis()
        val startOfDay = now - (now % (24 * 60 * 60 * 1000))
        val startOfMonth = java.util.Calendar.getInstance().apply {
            timeInMillis = now
            set(java.util.Calendar.DAY_OF_MONTH, 1)
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }.timeInMillis

        val todayCollection = paymentsList.filter { it.date >= startOfDay }.sumOf { it.amount }
        val monthCollection = paymentsList.filter { it.date >= startOfMonth }.sumOf { it.amount }

        val totalBeds = roomList.sumOf { it.capacity }
        val occupiedBeds = occupancyList.size
        val occupancyPercent = if (totalBeds == 0) 0 else ((occupiedBeds * 100.0) / totalBeds).toInt()

        val pendingAdmissions = studentsList.count { it.status == "PENDING" }

        DashboardKpis(
            todayCollection = todayCollection,
            monthCollection = monthCollection,
            occupancyPercent = occupancyPercent,
            pendingAdmissions = pendingAdmissions
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardKpis(0.0, 0.0, 0, 0))
}


