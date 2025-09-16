package com.example.collegeerp.data.sync

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.WriteQueueEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WriteQueueSyncer @Inject constructor(
    private val db: AppDatabase,
    private val firestore: FirebaseFirestore
) {
    private var job: Job? = null

    fun start(scope: CoroutineScope) {
        if (job?.isActive == true) return
        job = scope.launch(Dispatchers.IO) {
            while (isActive) {
                try {
                    val batch = db.queueDao().nextBatch()
                    if (batch.isNotEmpty()) {
                        for (item in batch) {
                            try {
                                firestore.collection(item.collection).document(item.documentId)
                                    .set(kotlinx.serialization.json.Json.parseToJsonElement(item.payloadJson))
                                db.queueDao().remove(item)
                            } catch (_: Exception) {
                                // Keep in queue for retry
                            }
                        }
                    }
                } catch (_: Exception) { }
                delay(3_000)
            }
        }
    }
}


