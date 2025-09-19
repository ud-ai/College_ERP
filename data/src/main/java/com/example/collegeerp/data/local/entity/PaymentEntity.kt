package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.collegeerp.domain.model.FeePayment

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey val paymentId: String,
    val studentId: String,
    val amount: Double,
    val date: Long,
    val method: String,
    val processedByUid: String,
    val receiptUrl: String?,
    val transactionNote: String?
)

fun PaymentEntity.toDomain(): FeePayment = FeePayment(
    paymentId = paymentId,
    studentId = studentId,
    amount = amount,
    date = date,
    method = method,
    processedByUid = processedByUid,
    receiptUrl = receiptUrl,
    transactionNote = transactionNote
)

fun FeePayment.toEntity(): PaymentEntity = PaymentEntity(
    paymentId = paymentId,
    studentId = studentId,
    amount = amount,
    date = date,
    method = method,
    processedByUid = processedByUid,
    receiptUrl = receiptUrl,
    transactionNote = transactionNote
)