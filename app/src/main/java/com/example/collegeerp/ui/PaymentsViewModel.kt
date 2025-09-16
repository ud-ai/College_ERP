package com.example.collegeerp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeerp.data.pdf.PdfReceiptGenerator
import com.example.collegeerp.domain.model.FeePayment
import com.example.collegeerp.domain.repository.PaymentRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val pdf: PdfReceiptGenerator
) : ViewModel() {

    fun recordPayment(studentId: String, amount: Double, method: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val paymentId = UUID.randomUUID().toString()
            val payment = FeePayment(
                paymentId = paymentId,
                studentId = studentId,
                amount = amount,
                date = System.currentTimeMillis(),
                method = method,
                processedByUid = "unknown",
                receiptUrl = null,
                transactionNote = null
            )

            // 1) Write to Firestore
            val paymentDoc = firestore.collection("payments").document(paymentId)
            paymentDoc.set(
                mapOf(
                    "paymentId" to payment.paymentId,
                    "studentId" to payment.studentId,
                    "amount" to payment.amount,
                    "date" to payment.date,
                    "method" to payment.method,
                    "processedBy" to payment.processedByUid,
                    "receiptUrl" to null,
                    "transactionNote" to payment.transactionNote
                )
            ).await()

            // 2) Generate PDF
            val bytes = pdf.generate(payment)

            // 3) Upload to Storage
            val ref = storage.reference.child("receipts/${paymentId}.pdf")
            ref.putBytes(bytes).await()
            val url = ref.downloadUrl.await().toString()

            // 4) Update Firestore with receipt URL
            paymentDoc.update("receiptUrl", url).await()

            // 5) Cache locally
            paymentRepository.recordPayment(payment.copy(receiptUrl = url))
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


