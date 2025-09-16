package com.example.collegeerp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collegeerp.data.local.entity.WriteQueueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QueueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enqueue(entity: WriteQueueEntity)

    @Query("SELECT * FROM write_queue ORDER BY timestamp ASC LIMIT :limit")
    suspend fun nextBatch(limit: Int = 20): List<WriteQueueEntity>

    @Delete
    suspend fun remove(vararg items: WriteQueueEntity)
}


