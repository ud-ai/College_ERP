package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String?,
    val createdAt: Long
)

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey val studentId: String,
    val fullName: String,
    val dob: Long,
    val program: String,
    val admissionDate: Long,
    val status: String,
    val photoUrl: String?,
    val contactPhone: String?,
    val guardianName: String?
)

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey val paymentId: String,
    val studentId: String,
    val amount: Double,
    val date: Long,
    val method: String,
    val processedByUid: String,
    val receiptUrl: String?,
    val transactionNote: String?
)

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey val roomKey: String, // hostelId_roomId
    val hostelId: String,
    val roomId: String,
    val roomNo: String,
    val capacity: Int
)

@Entity(tableName = "room_occupancy")
data class RoomOccupancyEntity(
    @PrimaryKey val key: String, // roomKey_studentId
    val roomKey: String,
    val studentId: String,
    val bedNo: String,
    val since: Long
)

@Entity(tableName = "exam_records")
data class ExamRecordEntity(
    @PrimaryKey val key: String, // examId_studentId
    val examId: String,
    val studentId: String,
    val grade: String,
    val remarks: String?
)


