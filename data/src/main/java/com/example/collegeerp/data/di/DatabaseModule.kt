package com.example.collegeerp.data.di

import android.content.Context
import androidx.room.Room
import com.example.collegeerp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
    
    @Provides
    fun provideStudentDao(database: AppDatabase) = database.studentDao()
    
    @Provides
    fun providePaymentDao(database: AppDatabase) = database.paymentDao()
    
    @Provides
    fun provideRoomDao(database: AppDatabase) = database.roomDao()
}


