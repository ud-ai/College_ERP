package com.example.collegeerp.data.local.dao

import androidx.room.*
import com.example.collegeerp.data.local.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payments WHERE studentId = :studentId ORDER BY date DESC")
    fun observeForStudent(studentId: String): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payments ORDER BY date DESC")
    fun observeAll(): Flow<List<PaymentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(payment: PaymentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(payments: List<PaymentEntity>)

    @Query("DELETE FROM payments")
    suspend fun deleteAll()
}