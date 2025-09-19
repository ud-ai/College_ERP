package com.example.collegeerp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeerp.domain.model.ExamRecord
import com.example.collegeerp.domain.repository.ExamRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val examRepository: ExamRepository
) : ViewModel() {

    fun upsertRecord(examId: String, studentId: String, grade: String, remarks: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = mapOf(
                "examId" to examId,
                "studentId" to studentId,
                "grade" to grade,
                "remarks" to remarks
            )
            firestore.collection("exams").document(examId)
                .collection("records").document(studentId)
                .set(data).await()
            examRepository.upsert(ExamRecord(examId, studentId, marks = emptyMap(), grade = grade, remarks = remarks))
        }
    }
}

private suspend fun <T> com.google.android.gms.tasks.Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) cont.resume(task.result)
            else cont.resumeWithException(task.exception ?: RuntimeException("Task failed"))
        }
    }
}


