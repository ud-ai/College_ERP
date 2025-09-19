package com.example.collegeerp.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.collegeerp.data.local.dao.PaymentDao
import com.example.collegeerp.data.local.dao.RoomDao
import com.example.collegeerp.data.local.dao.StudentDao
import com.example.collegeerp.data.local.entity.OccupancyEntity
import com.example.collegeerp.data.local.entity.PaymentEntity
import com.example.collegeerp.data.local.entity.RoomEntity
import com.example.collegeerp.data.local.entity.StudentEntity
import com.example.collegeerp.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        StudentEntity::class,
        PaymentEntity::class,
        RoomEntity::class,
        OccupancyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun paymentDao(): PaymentDao
    abstract fun roomDao(): RoomDao

    companion object {
        const val DATABASE_NAME = "college_erp_db"
    }
}