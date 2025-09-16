package com.example.collegeerp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.collegeerp.data.local.dao.*
import com.example.collegeerp.data.local.entity.*

/**
 * Minimal Room database; DAOs and entities will be added in the data-models task.
 */
@Database(
    entities = [
        UserEntity::class,
        StudentEntity::class,
        PaymentEntity::class,
        RoomEntity::class,
        RoomOccupancyEntity::class,
        ExamRecordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun studentDao(): StudentDao
    abstract fun paymentDao(): PaymentDao
    abstract fun roomDao(): RoomDao
    abstract fun examDao(): ExamDao
}


