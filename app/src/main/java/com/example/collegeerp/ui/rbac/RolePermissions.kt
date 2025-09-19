package com.example.collegeerp.ui.rbac

import com.example.collegeerp.domain.model.UserRole
import com.example.collegeerp.ui.navigation.Routes

object RolePermissions {
    private val adminRoutes = setOf(
        Routes.DASHBOARD,
        Routes.ADMISSIONS,
        Routes.STUDENTS,
        Routes.HOSTEL
    )

    private val admissionStaffRoutes = setOf(
        Routes.ADMISSIONS,
        Routes.STUDENTS,
        Routes.STUDENT_LIST
    )
    
    private val hostelStaffRoutes = setOf(
        Routes.HOSTEL,
        Routes.HOSTEL_ROOMS,
        Routes.HOSTEL_ALLOCATION
    )
    
    private val accountsStaffRoutes = setOf(
        Routes.PAYMENTS
    )
    
    private val examStaffRoutes = setOf(
        Routes.EXAMS,
        Routes.VIEW_EXAM_RECORDS,
        Routes.ADD_UPDATE_MARKS
    )

    private val studentRoutes = setOf(
        Routes.STUDENT_DASHBOARD,
        Routes.PROFILE,
        Routes.PAYMENTS
    )

    fun allowedRoutes(role: UserRole): Set<String> = when (role) {
        UserRole.ADMIN -> adminRoutes
        UserRole.ADMISSION_CELL -> admissionStaffRoutes
        UserRole.HOSTEL_MANAGER -> hostelStaffRoutes
        UserRole.ACCOUNTS -> accountsStaffRoutes
        UserRole.EXAM_STAFF -> examStaffRoutes
        UserRole.STUDENT -> studentRoutes
    }
}


