package com.example.collegeerp.data.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.collegeerp.domain.model.FeePayment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfReceiptGenerator @Inject constructor(
    private val context: Context
) {
    fun generate(payment: FeePayment): ByteArray {
        val doc = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 400, 1).create()
        val page = doc.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint()
        paint.textSize = 12f
        var y = 30f
        canvas.drawText("CollegeLightERP - Fee Receipt", 10f, y, paint); y += 20
        canvas.drawText("Payment ID: ${payment.paymentId}", 10f, y, paint); y += 16
        canvas.drawText("Student ID: ${payment.studentId}", 10f, y, paint); y += 16
        canvas.drawText("Amount: ${payment.amount}", 10f, y, paint); y += 16
        canvas.drawText("Method: ${payment.method}", 10f, y, paint); y += 16
        canvas.drawText("Date: ${payment.date}", 10f, y, paint); y += 16
        payment.transactionNote?.let { canvas.drawText("Note: $it", 10f, y, paint) }
        doc.finishPage(page)

        val out = java.io.ByteArrayOutputStream()
        doc.writeTo(out)
        doc.close()
        return out.toByteArray()
    }
}


