package com.example.collegeerp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
// Temporarily removed Firebase-dependent imports
// import javax.inject.Inject
// import com.example.collegeerp.data.sync.WriteQueueSyncer
// import com.example.collegeerp.data.sync.RealtimeMirror
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import com.google.firebase.FirebaseApp

@HiltAndroidApp
open class CollegeErpApplication : Application() {
    // Temporarily removed Firebase-dependent services
    // @Inject lateinit var writeQueueSyncer: WriteQueueSyncer
    // @Inject lateinit var realtimeMirror: RealtimeMirror
    private val appScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        // Temporarily disabled Firebase-dependent services
        // writeQueueSyncer.start(appScope)
        // realtimeMirror.start(appScope)
    }
}
