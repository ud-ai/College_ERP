package com.example.collegeerp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeerp.data.remote.StorageUploader
import com.example.collegeerp.domain.model.Student
import com.example.collegeerp.domain.repository.StudentRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AdmissionsViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val firestore: FirebaseFirestore,
    private val storageUploader: StorageUploader
) : ViewModel() {

    fun submitAdmission(fullName: String, program: String, photoData: ByteArray?) {
        viewModelScope.launch(Dispatchers.IO) {
            val studentId = UUID.randomUUID().toString()
            var photoUrl: String? = null
            if (photoData != null) {
                try { photoUrl = storageUploader.uploadStudentPhoto(studentId, photoData) } catch (_: Exception) { }
            }
            val student = Student(
                studentId = studentId,
                fullName = fullName,
                dob = 0L,
                program = program,
                admissionDate = System.currentTimeMillis(),
                status = "PENDING",
                photoUrl = photoUrl,
                contactPhone = null,
                guardianName = null,
                metadata = emptyMap()
            )
            // Firestore write
            firestore.collection("students").document(studentId).set(
                mapOf(
                    "studentId" to student.studentId,
                    "fullName" to student.fullName,
                    "dob" to student.dob,
                    "program" to student.program,
                    "admissionDate" to student.admissionDate,
                    "status" to student.status,
                    "photoUrl" to student.photoUrl
                )
            ).await()
            // Local cache
            studentRepository.upsert(student)
        }
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


