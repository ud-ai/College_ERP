package com.example.collegeerp

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.HiltAndroidApp

/**
 * Debug-only: connect Firebase SDKs to local emulators if available.
 */
class DebugCollegeErpApplication : CollegeErpApplication() {
    override fun onCreate() {
        super.onCreate()
        // Emulator connection disabled - using production Firebase
    }
}


