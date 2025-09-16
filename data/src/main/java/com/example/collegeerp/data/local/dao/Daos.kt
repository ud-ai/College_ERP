package com.example.collegeerp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collegeerp.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: UserEntity)

    @Query("SELECT * FROM users WHERE uid = :uid")
    fun observe(uid: String): Flow<UserEntity?>
}

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: StudentEntity)

    @Query("SELECT * FROM students WHERE studentId = :id")
    fun observe(id: String): Flow<StudentEntity?>

    @Query("SELECT * FROM students ORDER BY fullName ASC")
    fun observeAll(): Flow<List<StudentEntity>>
}

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PaymentEntity)

    @Query("SELECT * FROM payments WHERE studentId = :studentId ORDER BY date DESC")
    fun observeForStudent(studentId: String): Flow<List<PaymentEntity>>
}

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRoom(entity: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOccupancy(entity: RoomOccupancyEntity)

    @Query("SELECT * FROM rooms WHERE hostelId = :hostelId")
    fun observeRooms(hostelId: String): Flow<List<RoomEntity>>

    @Query("SELECT * FROM room_occupancy WHERE roomKey = :roomKey")
    fun observeOccupancy(roomKey: String): Flow<List<RoomOccupancyEntity>>
}

@Dao
interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ExamRecordEntity)

    @Query("SELECT * FROM exam_records WHERE examId = :examId AND studentId = :studentId")
    fun observe(examId: String, studentId: String): Flow<ExamRecordEntity?>
}


