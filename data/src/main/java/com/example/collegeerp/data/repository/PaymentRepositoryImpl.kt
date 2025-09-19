package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.toEntity
import com.example.collegeerp.data.local.entity.toDomain
import com.example.collegeerp.data.pdf.PdfGenerator
import com.example.collegeerp.domain.model.FeePayment
import com.example.collegeerp.domain.model.Student
import com.example.collegeerp.domain.repository.PaymentRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val pdfGenerator: PdfGenerator
) : PaymentRepository {

    override fun observePaymentsForStudent(studentId: String): Flow<List<FeePayment>> =
        db.paymentDao().observeForStudent(studentId).map { list -> list.map { it.toDomain() } }

    override suspend fun recordPayment(payment: FeePayment) {
        db.paymentDao().insert(payment.toEntity())
        try {
            firestore.collection("payments").document(payment.paymentId).set(payment)
        } catch (e: Exception) {
            android.util.Log.e("PaymentRepository", "Failed to sync payment to Firestore", e)
        }
    }
    
    suspend fun recordPaymentWithReceipt(payment: FeePayment, student: Student): FeePayment {
        // Generate PDF receipt
        val pdfPath = pdfGenerator.generatePaymentReceipt(payment, student)
        
        // Upload PDF to Firebase Storage
        val receiptUrl = uploadReceiptToStorage(pdfPath, payment.paymentId)
        
        // Update payment with receipt URL
        val updatedPayment = payment.copy(receiptUrl = receiptUrl)
        
        // Save to database and Firestore
        recordPayment(updatedPayment)
        
        return updatedPayment
    }
    
    private suspend fun uploadReceiptToStorage(pdfPath: String, paymentId: String): String? {
        return try {
            val file = File(pdfPath)
            val storageRef = storage.reference.child("receipts/$paymentId.pdf")
            // Note: In a real implementation, you'd use Firebase Storage upload task
            // For now, return a placeholder URL
            "https://storage.googleapis.com/receipts/$paymentId.pdf"
        } catch (e: Exception) {
            android.util.Log.e("PaymentRepository", "Failed to upload receipt", e)
            null
        }
    }
}



