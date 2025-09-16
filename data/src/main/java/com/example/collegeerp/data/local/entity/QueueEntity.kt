package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "write_queue")
data class WriteQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val collection: String,
    val documentId: String,
    val payloadJson: String,
    val timestamp: Long = System.currentTimeMillis()
)


