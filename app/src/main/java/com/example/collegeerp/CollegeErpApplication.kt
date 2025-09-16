package com.example.collegeerp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.example.collegeerp.data.sync.WriteQueueSyncer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class CollegeErpApplication : Application() {
    @Inject lateinit var writeQueueSyncer: WriteQueueSyncer
    private val appScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        writeQueueSyncer.start(appScope)
    }
}
