package com.example.collegeerp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetailScreen(
    studentId: String,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        item {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Student Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        item {
            // Profile Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Image
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF333333)),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Ethan Carter",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Text(
                    text = "Student ID: 202300123",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Text(
                    text = "Class: 12th A",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Personal Information
            SectionHeader("Personal Information")
            InfoGrid(
                listOf(
                    "Date of Birth" to "15th May 2005",
                    "Gender" to "Male",
                    "Nationality" to "American",
                    "Religion" to "Christian",
                    "Blood Group" to "O+",
                    "Category" to "General"
                )
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Contact Details
            SectionHeader("Contact Details")
            InfoGrid(
                listOf(
                    "Phone" to "+1 (555) 123-4567",
                    "Email" to "ethan.carter@example.com",
                    "Address" to "123 Elm Street, Anytown, USA"
                )
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Guardian Information
            SectionHeader("Guardian Information")
            InfoGrid(
                listOf(
                    "Father's Name" to "Robert Carter",
                    "Mother's Name" to "Olivia Carter",
                    "Guardian's Phone" to "+1 (555) 987-6543"
                )
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Admission Details
            SectionHeader("Admission Details")
            InfoGrid(
                listOf(
                    "Admission Date" to "1st Sep 2023",
                    "Course" to "Science",
                    "Batch" to "2023-2024"
                )
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Documents
            SectionHeader("Documents")
            DocumentItem(Icons.Default.Info, "High School Transcript")
            DocumentItem(Icons.Default.AccountBox, "ID Proof")
            DocumentItem(Icons.Default.LocationOn, "Address Proof")
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quick Actions
            QuickActionItem(Icons.Default.AccountBox, "Payments")
            QuickActionItem(Icons.Default.Home, "Hostel")
            QuickActionItem(Icons.Default.Star, "Exams")
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Export PDF Button
            Button(
                onClick = { 
                    generateStudentPDF(context, "Ethan Carter", studentId)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text(
                    text = "Export PDF",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun InfoGrid(items: List<Pair<String, String>>) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { (label, value) ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = value,
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                // Fill remaining space if odd number of items
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun DocumentItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2196F3),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun QuickActionItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2196F3),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

fun generateStudentPDF(context: Context, studentName: String, studentId: String) {
    try {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        
        val paint = Paint().apply {
            textSize = 16f
            color = android.graphics.Color.BLACK
        }
        
        val titlePaint = Paint().apply {
            textSize = 24f
            color = android.graphics.Color.BLACK
            isFakeBoldText = true
        }
        
        var yPosition = 50f
        
        // Title
        canvas.drawText("Student Profile", 50f, yPosition, titlePaint)
        yPosition += 40f
        
        // Student Info
        canvas.drawText("Name: $studentName", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Student ID: 202300123", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Class: 12th A", 50f, yPosition, paint)
        yPosition += 40f
        
        // Personal Information
        canvas.drawText("Personal Information", 50f, yPosition, titlePaint)
        yPosition += 30f
        canvas.drawText("Date of Birth: 15th May 2005", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Gender: Male", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Nationality: American", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Religion: Christian", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Blood Group: O+", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Category: General", 50f, yPosition, paint)
        yPosition += 40f
        
        // Contact Details
        canvas.drawText("Contact Details", 50f, yPosition, titlePaint)
        yPosition += 30f
        canvas.drawText("Phone: +1 (555) 123-4567", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Email: ethan.carter@example.com", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Address: 123 Elm Street, Anytown, USA", 50f, yPosition, paint)
        yPosition += 40f
        
        // Guardian Information
        canvas.drawText("Guardian Information", 50f, yPosition, titlePaint)
        yPosition += 30f
        canvas.drawText("Father's Name: Robert Carter", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Mother's Name: Olivia Carter", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Guardian's Phone: +1 (555) 987-6543", 50f, yPosition, paint)
        yPosition += 40f
        
        // Admission Details
        canvas.drawText("Admission Details", 50f, yPosition, titlePaint)
        yPosition += 30f
        canvas.drawText("Admission Date: 1st Sep 2023", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Course: Science", 50f, yPosition, paint)
        yPosition += 25f
        canvas.drawText("Batch: 2023-2024", 50f, yPosition, paint)
        
        pdfDocument.finishPage(page)
        
        // Save PDF
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "Student_Profile_${studentName.replace(" ", "_")}.pdf")
        
        val fileOutputStream = FileOutputStream(file)
        pdfDocument.writeTo(fileOutputStream)
        pdfDocument.close()
        fileOutputStream.close()
        
        Toast.makeText(context, "PDF saved to Downloads folder", Toast.LENGTH_LONG).show()
        
        // Open PDF
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(android.net.Uri.fromFile(file), "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        }
        
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No PDF viewer found", Toast.LENGTH_SHORT).show()
        }
        
    } catch (e: IOException) {
        Toast.makeText(context, "Error generating PDF: ${e.message}", Toast.LENGTH_LONG).show()
    }
}


