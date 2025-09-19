package com.example.collegeerp.domain.repository

import com.example.collegeerp.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserFlow: Flow<AppUser?>
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String, name: String): String
    suspend fun signOut()
}

interface StudentRepository {
    fun observeStudents(): Flow<List<Student>>
    fun observeStudent(studentId: String): Flow<Student?>
    suspend fun upsert(student: Student)
}

interface PaymentRepository {
    fun observePaymentsForStudent(studentId: String): Flow<List<FeePayment>>
    suspend fun recordPayment(payment: FeePayment)
}

interface HostelRepository {
    // For simplicity, expose rooms list by hostel
    // Occupancy merging handled at higher layer later.
    // This keeps the initial compile surface minimal.
    fun observeRooms(hostelId: String): Flow<List<HostelRoom>>
}

interface ExamRepository {
    fun observeExamRecord(examId: String, studentId: String): Flow<ExamRecord?>
    suspend fun upsert(record: ExamRecord)
}


