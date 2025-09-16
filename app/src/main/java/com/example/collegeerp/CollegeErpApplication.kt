package com.example.collegeerp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.example.collegeerp.data.sync.WriteQueueSyncer
import com.example.collegeerp.data.sync.RealtimeMirror
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class CollegeErpApplication : Application() {
    @Inject lateinit var writeQueueSyncer: WriteQueueSyncer
    @Inject lateinit var realtimeMirror: RealtimeMirror
    private val appScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        writeQueueSyncer.start(appScope)
        realtimeMirror.start(appScope)
    }
}
