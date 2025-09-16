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

    private val staffRoutes = setOf(
        Routes.ADMISSIONS,
        Routes.STUDENTS,
        Routes.HOSTEL
    )

    private val studentRoutes = setOf(
        Routes.STUDENT_HOME
    )

    fun allowedRoutes(role: UserRole): Set<String> = when (role) {
        UserRole.ADMIN -> adminRoutes
        UserRole.STAFF -> staffRoutes
        UserRole.STUDENT -> studentRoutes
    }
}


