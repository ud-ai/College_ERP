package com.example.collegeerp.data.local.dao

import androidx.room.*
import com.example.collegeerp.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY fullName ASC")
    fun observeAll(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE studentId = :studentId")
    fun observe(studentId: String): Flow<StudentEntity?>

    @Query("SELECT * FROM students WHERE studentId = :studentId")
    suspend fun getById(studentId: String): StudentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: StudentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(students: List<StudentEntity>)

    @Delete
    suspend fun delete(student: StudentEntity)

    @Query("DELETE FROM students")
    suspend fun deleteAll()
}