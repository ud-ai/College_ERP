package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey val roomId: String,
    val hostelId: String,
    val roomNo: String,
    val capacity: Int
)

@Entity(tableName = "occupancy")
data class OccupancyEntity(
    @PrimaryKey val id: String,
    val roomId: String,
    val studentId: String,
    val bedNo: String,
    val since: Long
)