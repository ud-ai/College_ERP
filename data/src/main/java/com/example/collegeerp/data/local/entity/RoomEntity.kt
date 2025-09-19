package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey val roomKey: String, // hostelId_roomId
    val hostelId: String,
    val roomId: String,
    val roomNo: String,
    val capacity: Int
)

@Entity(tableName = "room_occupancy")
data class RoomOccupancyEntity(
    @PrimaryKey val key: String, // roomKey_studentId
    val roomKey: String,
    val studentId: String,
    val bedNo: String,
    val since: Long
)