package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_records")
data class ExamRecordEntity(
    @PrimaryKey val key: String, // examId_studentId
    val examId: String,
    val studentId: String,
    val grade: String,
    val remarks: String?
)