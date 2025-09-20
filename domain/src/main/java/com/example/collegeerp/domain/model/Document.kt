package com.example.collegeerp.domain.model

data class Document(
    val id: String = "",
    val name: String = "",
    val type: DocumentType = DocumentType.OTHER,
    val url: String = "",
    val uploadedBy: String = "",
    val uploadedAt: Long = System.currentTimeMillis(),
    val size: Long = 0,
    val studentId: String? = null
)

enum class DocumentType {
    PHOTO, AADHAR, MARKSHEET, CERTIFICATE, RECEIPT, OTHER
}