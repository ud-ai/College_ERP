package com.example.collegeerp.data.local.dao

import androidx.room.*
import com.example.collegeerp.data.local.entity.ExamRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Query("SELECT * FROM exam_records WHERE examId = :examId AND studentId = :studentId")
    fun observe(examId: String, studentId: String): Flow<ExamRecordEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: ExamRecordEntity)

    @Query("SELECT * FROM exam_records WHERE studentId = :studentId")
    fun observeForStudent(studentId: String): Flow<List<ExamRecordEntity>>
}