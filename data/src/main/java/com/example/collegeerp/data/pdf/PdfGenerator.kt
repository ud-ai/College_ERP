package com.example.collegeerp.data.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.collegeerp.domain.model.FeePayment
import com.example.collegeerp.domain.model.Student
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfGenerator @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    /**
     * Generate a PDF receipt for a fee payment
     * @return File path of the generated PDF
     */
    suspend fun generatePaymentReceipt(
        payment: FeePayment,
        student: Student
    ): String {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
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
        
        // Draw receipt content
        var yPosition = 50f
        
        // Title
        canvas.drawText("COLLEGE ERP - FEE RECEIPT", 50f, yPosition, titlePaint)
        yPosition += 40f
        
        // Receipt details
        canvas.drawText("Receipt ID: ${payment.paymentId}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Date: ${dateFormat.format(Date(payment.date))}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Student: ${student.fullName}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Student ID: ${student.studentId}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Program: ${student.program}", 50f, yPosition, paint)
        yPosition += 40f
        
        // Payment details
        canvas.drawText("Amount Paid: $${String.format("%.2f", payment.amount)}", 50f, yPosition, paint)
        yPosition += 25f
        
        canvas.drawText("Payment Method: ${payment.method}", 50f, yPosition, paint)
        yPosition += 25f
        
        payment.transactionNote?.let {
            canvas.drawText("Note: $it", 50f, yPosition, paint)
            yPosition += 25f
        }
        
        yPosition += 40f
        canvas.drawText("Generated on: ${dateFormat.format(Date())}", 50f, yPosition, paint)
        
        document.finishPage(page)
        
        // Save to file
        val fileName = "receipt_${payment.paymentId}.pdf"
        val file = File(context.cacheDir, fileName)
        
        FileOutputStream(file).use { outputStream ->
            document.writeTo(outputStream)
        }
        
        document.close()
        return file.absolutePath
    }
}