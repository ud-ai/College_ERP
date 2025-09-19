package com.example.collegeerp.data.local.dao

import androidx.room.*
import com.example.collegeerp.data.local.entity.RoomOccupancyEntity
import com.example.collegeerp.data.local.entity.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT * FROM rooms")
    fun observeAllRooms(): Flow<List<RoomEntity>>

    @Query("SELECT * FROM room_occupancy")
    fun observeAllOccupancy(): Flow<List<RoomOccupancyEntity>>

    @Query("SELECT * FROM rooms WHERE hostelId = :hostelId")
    fun observeRoomsByHostel(hostelId: String): Flow<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOccupancy(occupancy: RoomOccupancyEntity)

    @Delete
    suspend fun deleteOccupancy(occupancy: RoomOccupancyEntity)
}