package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.toEntity
import com.example.collegeerp.data.local.entity.toDomain
import com.example.collegeerp.domain.model.Student
import com.example.collegeerp.domain.repository.StudentRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val firestore: FirebaseFirestore
) : StudentRepository {

    override fun observeStudents(): Flow<List<Student>> =
        db.studentDao().observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeStudent(studentId: String): Flow<Student?> =
        db.studentDao().observe(studentId).map { it?.toDomain() }

    override suspend fun upsert(student: Student) {
        db.studentDao().insert(student.toEntity())
        try {
            firestore.collection("students").document(student.studentId).set(student)
        } catch (e: Exception) {
            android.util.Log.e("StudentRepository", "Failed to sync to Firestore", e)
        }
    }
}



