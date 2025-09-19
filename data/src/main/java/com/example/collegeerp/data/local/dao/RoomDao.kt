package com.example.collegeerp.data.local.dao

import androidx.room.*
import com.example.collegeerp.data.local.entity.OccupancyEntity
import com.example.collegeerp.data.local.entity.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT * FROM rooms")
    fun observeAllRooms(): Flow<List<RoomEntity>>

    @Query("SELECT * FROM occupancy")
    fun observeAllOccupancy(): Flow<List<OccupancyEntity>>

    @Query("SELECT * FROM rooms WHERE hostelId = :hostelId")
    fun observeRoomsByHostel(hostelId: String): Flow<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOccupancy(occupancy: OccupancyEntity)

    @Delete
    suspend fun deleteOccupancy(occupancy: OccupancyEntity)
}