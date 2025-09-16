package com.example.collegeerp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Minimal Room database; DAOs and entities will be added in the data-models task.
 */
@Database(entities = [], version = 1)
abstract class AppDatabase : RoomDatabase()


