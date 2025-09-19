package com.example.collegeerp.data.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.collegeerp.domain.model.FeePayment
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfReceiptGenerator @Inject constructor(
    private val context: Context
) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    fun generate(payment: FeePayment): ByteArray {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        
        val canvas = page.canvas
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
        }
        
        val titlePaint = Paint().apply {
            color = Color.BLACK
            textSize = 18f
            isFakeBoldText = true
        }
        
        var yPosition = 50f
        
        canvas.drawText("COLLEGE ERP - FEE RECEIPT", 50f, yPosition, titlePaint)
        yPosition += 40f
        
        canvas.drawText("Receipt ID: ${payment.paymentId}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Date: ${dateFormat.format(Date(payment.date))}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Student ID: ${payment.studentId}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Amount: $${String.format("%.2f", payment.amount)}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Method: ${payment.method}", 50f, yPosition, paint)
        
        document.finishPage(page)
        
        val outputStream = ByteArrayOutputStream()
        document.writeTo(outputStream)
        document.close()
        
        return outputStream.toByteArray()
    }
}