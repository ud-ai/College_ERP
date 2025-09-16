package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.StudentEntity
import com.example.collegeerp.domain.model.Student
import com.example.collegeerp.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepositoryImpl @Inject constructor(
    private val db: AppDatabase
) : StudentRepository {

    override fun observeStudents(): Flow<List<Student>> =
        db.studentDao().observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeStudent(studentId: String): Flow<Student?> =
        db.studentDao().observe(studentId).map { it?.toDomain() }

    override suspend fun upsert(student: Student) {
        db.studentDao().upsert(student.toEntity())
        // Firestore sync to be added in a later step
    }
}

private fun StudentEntity.toDomain(): Student = Student(
    studentId = studentId,
    fullName = fullName,
    dob = dob,
    program = program,
    admissionDate = admissionDate,
    status = status,
    photoUrl = photoUrl,
    contactPhone = contactPhone,
    guardianName = guardianName,
    metadata = emptyMap()
)

private fun Student.toEntity(): StudentEntity = StudentEntity(
    studentId = studentId,
    fullName = fullName,
    dob = dob,
    program = program,
    admissionDate = admissionDate,
    status = status,
    photoUrl = photoUrl,
    contactPhone = contactPhone,
    guardianName = guardianName
)


