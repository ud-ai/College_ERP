package com.example.collegeerp.data.remote

import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageUploader @Inject constructor(
    private val storage: FirebaseStorage
) {
    suspend fun uploadStudentPhoto(studentId: String, bytes: ByteArray): String {
        val ref = storage.reference.child("students/$studentId/photo.jpg")
        ref.putBytes(bytes).await()
        return ref.downloadUrl.await().toString()
    }
}

private suspend fun <T> com.google.android.gms.tasks.Task<T>.await(): T {
    return kotlinx.coroutines.suspendCancellableCoroutine { cont ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) cont.resume(task.result, null)
            else cont.resumeWithException(task.exception ?: RuntimeException("Task failed"))
        }
    }
}


