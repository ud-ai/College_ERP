package com.example.collegeerp.data.di

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides Firebase singletons. In debug builds, can be configured to use local emulators.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseApp(application: Application): FirebaseApp {
        return FirebaseApp.initializeApp(application) ?: FirebaseApp.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuth(app: FirebaseApp): FirebaseAuth {
        return FirebaseAuth.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideFirestore(app: FirebaseApp): FirebaseFirestore {
        return FirebaseFirestore.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideStorage(app: FirebaseApp): FirebaseStorage {
        return FirebaseStorage.getInstance(app)
    }
}


