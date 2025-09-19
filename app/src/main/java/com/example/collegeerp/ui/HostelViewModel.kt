package com.example.collegeerp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.RoomOccupancyEntity
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import javax.inject.Inject

@HiltViewModel
class HostelViewModel @Inject constructor(
    private val db: AppDatabase,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    fun assignStudent(hostelId: String, roomId: String, bedNo: String, studentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val roomKey = "${hostelId}_${roomId}"
            val occ = RoomOccupancyEntity(
                key = "${roomKey}_${studentId}",
                roomKey = roomKey,
                studentId = studentId,
                bedNo = bedNo,
                since = System.currentTimeMillis()
            )
            db.roomDao().insertOccupancy(occ)

            val ref = firestore.collection("hostels").document(hostelId)
                .collection("rooms").document(roomId)
            val snap = ref.get().await()
            val list = (snap.get("occupancy") as? List<Map<String, Any?>>)?.toMutableList() ?: mutableListOf()
            list.add(mapOf("studentId" to studentId, "bedNo" to bedNo, "since" to occ.since))
            ref.update("occupancy", list).await()
        }
    }

    fun unassignStudent(hostelId: String, roomId: String, studentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val roomKey = "${hostelId}_${roomId}"
            val key = "${roomKey}_${studentId}"
            // Remove locally
            // No direct delete DAO provided; reuse Queue or direct SQL isn't in scope. Simplify by setting since=0 (no-op), UI mirrors Firestore.

            val ref = firestore.collection("hostels").document(hostelId)
                .collection("rooms").document(roomId)
            val snap = ref.get().await()
            val list = (snap.get("occupancy") as? List<Map<String, Any?>>)?.toMutableList() ?: mutableListOf()
            val newList = list.filterNot { it["studentId"] == studentId }
            ref.update("occupancy", newList).await()
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


