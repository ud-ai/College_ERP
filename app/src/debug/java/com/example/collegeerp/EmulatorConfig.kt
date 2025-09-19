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
        // Defaults for emulator suite.
        val host = "10.0.2.2" // Android emulator -> host
        FirebaseAuth.getInstance().useEmulator(host, 9099)
        FirebaseFirestore.getInstance().useEmulator(host, 8080)
        FirebaseStorage.getInstance().useEmulator(host, 9199)
    }
}


