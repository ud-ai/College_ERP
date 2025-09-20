package com.example.collegeerp.domain.model

data class Notification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val type: NotificationType = NotificationType.INFO,
    val userId: String = "",
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis(),
    val actionData: Map<String, String> = emptyMap()
)

enum class NotificationType {
    INFO, SUCCESS, WARNING, ERROR, PAYMENT, EXAM, ADMISSION, HOSTEL
}