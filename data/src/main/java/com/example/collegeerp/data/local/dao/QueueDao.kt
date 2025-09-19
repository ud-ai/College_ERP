package com.example.collegeerp.data.local.dao

import androidx.room.*
import com.example.collegeerp.data.local.entity.WriteQueueEntity

@Dao
interface QueueDao {
    @Query("SELECT * FROM write_queue ORDER BY timestamp ASC LIMIT 10")
    suspend fun nextBatch(): List<WriteQueueEntity>

    @Insert
    suspend fun insert(item: WriteQueueEntity)

    @Delete
    suspend fun remove(item: WriteQueueEntity)

    @Query("DELETE FROM write_queue")
    suspend fun clear()
}