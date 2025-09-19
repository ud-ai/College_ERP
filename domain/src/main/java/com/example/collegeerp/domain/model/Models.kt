package com.example.collegeerp.domain.model

import java.util.Date

/** Roles supported by the app for RBAC. */
enum class UserRole { 
    STUDENT,           // Student login - profile, attendance, marks, fees
    ADMISSION_CELL,    // Admission cell - manage new admissions
    ACCOUNTS,          // Accounts department - manage fees and payments
    HOSTEL_MANAGER,    // Hostel manager - manage hostel operations
    EXAM_STAFF,        // Exam department - manage exams and marks
    ADMIN              // Full access to everything
}

data class AppUser(
    val uid: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val phone: String?,
    val createdAt: Long
)

data class FeePayment(
    val paymentId: String,
    val studentId: String,
    val amount: Double,
    val date: Long,
    val method: String,
    val processedByUid: String,
    val receiptUrl: String?,
    val transactionNote: String?
)

data class Student(
    val studentId: String,
    val fullName: String,
    val dob: Long,
    val program: String,
    val admissionDate: Long,
    val status: String,
    val photoUrl: String?,
    val contactPhone: String?,
    val guardianName: String?,
    val metadata: Map<String, String> = emptyMap()
)

data class HostelOccupancy(
    val studentId: String,
    val bedNo: String,
    val since: Long
)

data class HostelRoom(
    val hostelId: String,
    val roomId: String,
    val roomNo: String,
    val capacity: Int,
    val occupancy: List<HostelOccupancy> = emptyList()
)

data class ExamRecord(
    val examId: String,
    val studentId: String,
    val marks: Map<String, Double>,
    val grade: String,
    val remarks: String?
)

data class AuditLog(
    val logId: String,
    val action: String,
    val entity: String,
    val byUid: String,
    val at: Long,
    val details: Map<String, String> = emptyMap()
)


