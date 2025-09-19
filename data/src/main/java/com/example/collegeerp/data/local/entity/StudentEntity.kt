package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.collegeerp.domain.model.Student

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
    val guardianName: String?,
    val metadataJson: String = "{}"
)

fun StudentEntity.toDomain(): Student = Student(
    studentId = studentId,
    fullName = fullName,
    dob = dob,
    program = program,
    admissionDate = admissionDate,
    status = status,
    photoUrl = photoUrl,
    contactPhone = contactPhone,
    guardianName = guardianName,
    metadata = emptyMap() // TODO: Parse JSON if needed
)

fun Student.toEntity(): StudentEntity = StudentEntity(
    studentId = studentId,
    fullName = fullName,
    dob = dob,
    program = program,
    admissionDate = admissionDate,
    status = status,
    photoUrl = photoUrl,
    contactPhone = contactPhone,
    guardianName = guardianName,
    metadataJson = "{}" // TODO: Serialize metadata if needed
)