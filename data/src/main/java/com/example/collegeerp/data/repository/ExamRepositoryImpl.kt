package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.ExamRecordEntity
import com.example.collegeerp.domain.model.ExamRecord
import com.example.collegeerp.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExamRepositoryImpl @Inject constructor(
    private val db: AppDatabase
) : ExamRepository {
    override fun observeExamRecord(examId: String, studentId: String): Flow<ExamRecord?> =
        db.examDao().observe(examId, studentId).map { it?.toDomain() }

    override suspend fun upsert(record: ExamRecord) {
        db.examDao().upsert(record.toEntity())
    }
}

private fun ExamRecordEntity.toDomain(): ExamRecord = ExamRecord(
    examId = examId,
    studentId = studentId,
    marks = emptyMap(),
    grade = grade,
    remarks = remarks
)

private fun ExamRecord.toEntity(): ExamRecordEntity = ExamRecordEntity(
    key = "$examId_$studentId",
    examId = examId,
    studentId = studentId,
    grade = grade,
    remarks = remarks
)


