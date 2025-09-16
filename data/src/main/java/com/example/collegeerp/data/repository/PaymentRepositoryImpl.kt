package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.PaymentEntity
import com.example.collegeerp.domain.model.FeePayment
import com.example.collegeerp.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepositoryImpl @Inject constructor(
    private val db: AppDatabase
) : PaymentRepository {

    override fun observePaymentsForStudent(studentId: String): Flow<List<FeePayment>> =
        db.paymentDao().observeForStudent(studentId).map { list -> list.map { it.toDomain() } }

    override suspend fun recordPayment(payment: FeePayment) {
        db.paymentDao().upsert(payment.toEntity())
        // Firestore sync to be added in a later step
    }
}

private fun PaymentEntity.toDomain(): FeePayment = FeePayment(
    paymentId = paymentId,
    studentId = studentId,
    amount = amount,
    date = date,
    method = method,
    processedByUid = processedByUid,
    receiptUrl = receiptUrl,
    transactionNote = transactionNote
)

private fun FeePayment.toEntity(): PaymentEntity = PaymentEntity(
    paymentId = paymentId,
    studentId = studentId,
    amount = amount,
    date = date,
    method = method,
    processedByUid = processedByUid,
    receiptUrl = receiptUrl,
    transactionNote = transactionNote
)


