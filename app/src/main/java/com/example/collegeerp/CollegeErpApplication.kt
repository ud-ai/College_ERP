package com.example.collegeerp

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class CollegeErpApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            val app = FirebaseApp.initializeApp(this)
            android.util.Log.d("CollegeErpApp", "Firebase initialized: ${app?.name}")
        } catch (e: Exception) {
            android.util.Log.e("CollegeErpApp", "Firebase initialization failed", e)
        }
    }
}
