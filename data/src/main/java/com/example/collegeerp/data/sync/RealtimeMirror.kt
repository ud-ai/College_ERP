package com.example.collegeerp.data.sync

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.PaymentEntity
import com.example.collegeerp.data.local.entity.StudentEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealtimeMirror @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val db: AppDatabase
) {
    private var studentReg: ListenerRegistration? = null
    private var paymentReg: ListenerRegistration? = null

    fun start(scope: CoroutineScope) {
        // Students
        studentReg = firestore.collection("students").addSnapshotListener { snap, _ ->
            if (snap == null) return@addSnapshotListener
            scope.launch(Dispatchers.IO) {
                snap.documents.forEach { doc ->
                    val e = StudentEntity(
                        studentId = doc.getString("studentId") ?: doc.id,
                        fullName = doc.getString("fullName") ?: "",
                        dob = (doc.getLong("dob") ?: 0L),
                        program = doc.getString("program") ?: "",
                        admissionDate = (doc.getLong("admissionDate") ?: 0L),
                        status = doc.getString("status") ?: "PENDING",
                        photoUrl = doc.getString("photoUrl"),
                        contactPhone = null,
                        guardianName = null
                    )
                    db.studentDao().upsert(e)
                }
            }
        }
        // Payments
        paymentReg = firestore.collection("payments").addSnapshotListener { snap, _ ->
            if (snap == null) return@addSnapshotListener
            scope.launch(Dispatchers.IO) {
                snap.documents.forEach { doc ->
                    val e = PaymentEntity(
                        paymentId = doc.getString("paymentId") ?: doc.id,
                        studentId = doc.getString("studentId") ?: "",
                        amount = (doc.getDouble("amount") ?: 0.0),
                        date = (doc.getLong("date") ?: 0L),
                        method = doc.getString("method") ?: "",
                        processedByUid = doc.getString("processedBy") ?: "",
                        receiptUrl = doc.getString("receiptUrl"),
                        transactionNote = doc.getString("transactionNote")
                    )
                    db.paymentDao().upsert(e)
                }
            }
        }
    }

    fun stop() {
        studentReg?.remove(); studentReg = null
        paymentReg?.remove(); paymentReg = null
    }
}


