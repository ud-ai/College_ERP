package com.example.collegeerp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.collegeerp.ui.theme.CollegeERPTheme
import com.example.collegeerp.ui.theme.rememberThemeManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeerp.domain.model.UserRole
import com.example.collegeerp.ui.AuthViewModel
import com.example.collegeerp.ui.navigation.Routes
import com.example.collegeerp.ui.rbac.RolePermissions
import com.example.collegeerp.ui.screens.AdminHome
import com.example.collegeerp.ui.screens.AuthScreen
import com.example.collegeerp.ui.screens.AdmissionsScreen
import com.example.collegeerp.ui.screens.DashboardScreen
import com.example.collegeerp.ui.screens.HostelScreen
import com.example.collegeerp.ui.screens.PaymentsScreen
import com.example.collegeerp.ui.screens.StaffHome
import com.example.collegeerp.ui.screens.StudentHome
import com.example.collegeerp.ui.screens.StudentDetailScreen
import com.example.collegeerp.ui.screens.StudentsScreen
import com.example.collegeerp.ui.screens.ExamScreen
import com.example.collegeerp.ui.screens.ProfileScreen
import com.example.collegeerp.ui.screens.PersonalDetailsScreen
import com.example.collegeerp.ui.screens.HostelRoomsScreen
import com.example.collegeerp.ui.screens.HostelAllocationScreen
import com.example.collegeerp.ui.screens.ViewStudentExamRecordsScreen
import com.example.collegeerp.ui.screens.AddUpdateMarksScreen
import com.example.collegeerp.ui.screens.StudentDashboard
import com.example.collegeerp.ui.screens.AdmissionStaffDashboard
import com.example.collegeerp.ui.screens.HostelStaffDashboard
import com.example.collegeerp.ui.screens.AccountsStaffDashboard
import com.example.collegeerp.ui.screens.ExamStaffDashboard
import com.example.collegeerp.ui.screens.AdminDashboard
import com.example.collegeerp.ui.screens.NotificationsScreen
import com.example.collegeerp.ui.screens.AnalyticsScreen
import com.example.collegeerp.ui.screens.DocumentsScreen
import com.example.collegeerp.ui.screens.AttendanceScreen
import com.example.collegeerp.ui.screens.PendingApplicationsScreen
import com.example.collegeerp.ui.screens.AdmissionReportsScreen
import com.example.collegeerp.ui.screens.HostelMaintenanceScreen
import com.example.collegeerp.ui.screens.HostelReportsScreen
import com.example.collegeerp.ui.screens.PaymentRecordsScreen
import com.example.collegeerp.ui.screens.FinancialReportsScreen
import com.example.collegeerp.ui.screens.OutstandingDuesScreen
import com.example.collegeerp.ui.screens.ResultAnalysisScreen
import com.example.collegeerp.ui.screens.TranscriptsScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val themeManager = rememberThemeManager()
			val isDarkMode by themeManager.isDarkMode.collectAsState(initial = false)
			
			CollegeERPTheme(darkTheme = isDarkMode) {
				Surface(color = MaterialTheme.colorScheme.background) {
					AppNav(themeManager = themeManager)
				}
			}
		}
	}
}

@Composable
fun AppNav(
    themeManager: com.example.collegeerp.ui.theme.ThemeManager,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val isDarkMode by themeManager.isDarkMode.collectAsState(initial = false)
    val navController = rememberNavController()
    val user by viewModel.currentUser.collectAsState()

    NavHost(navController = navController, startDestination = Routes.AUTH) {
        composable(Routes.AUTH) {
            AuthScreen(
                onSignIn = { email, password -> viewModel.signIn(email, password) },
                themeManager = themeManager
            )
            
            // Handle navigation based on user role using LaunchedEffect to avoid navigating during composition
            LaunchedEffect(user) {
                val u = user
                if (u != null) {
                    android.util.Log.d("MainActivity", "User logged in with role: ${u.role}")
                    
                    // Force admin role for testing if email contains admin
                    val actualRole = if (u.email.contains("admin", ignoreCase = true)) {
                        android.util.Log.d("MainActivity", "Forcing ADMIN role for admin email")
                        UserRole.ADMIN
                    } else {
                        u.role
                    }
                    
                    when (actualRole) {
                        UserRole.ADMIN -> {
                            android.util.Log.d("MainActivity", "Navigating to ADMIN_DASHBOARD")
                            navController.navigate(Routes.ADMIN_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        }
                        UserRole.STUDENT -> {
                            android.util.Log.d("MainActivity", "Navigating to STUDENT_DASHBOARD")
                            navController.navigate(Routes.STUDENT_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        }
                        UserRole.ADMISSION_CELL -> {
                            android.util.Log.d("MainActivity", "Navigating to ADMISSION_DASHBOARD")
                            navController.navigate(Routes.ADMISSION_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        }
                        UserRole.HOSTEL_MANAGER -> {
                            android.util.Log.d("MainActivity", "Navigating to HOSTEL_DASHBOARD")
                            navController.navigate(Routes.HOSTEL_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        }
                        UserRole.ACCOUNTS -> {
                            android.util.Log.d("MainActivity", "Navigating to ACCOUNTS_DASHBOARD")
                            navController.navigate(Routes.ACCOUNTS_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        }
                        UserRole.EXAM_STAFF -> {
                            android.util.Log.d("MainActivity", "Navigating to EXAM_DASHBOARD")
                            navController.navigate(Routes.EXAM_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        }
                    }
                }
            }
        }
        composable(Routes.ADMIN_HOME) {
            AdminHome(
                onNavigate = { route -> if (routeAllowed(viewModel, route)) navController.navigate(route) },
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.STAFF_HOME) {
            StaffHome(
                onNavigate = { route -> if (routeAllowed(viewModel, route)) navController.navigate(route) },
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.STUDENT_HOME) { 
            StudentHome(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToFees = { navController.navigate(Routes.PAYMENTS) },
                onNavigateToHostel = { navController.navigate(Routes.HOSTEL) },
                onNavigateToExams = { navController.navigate(Routes.EXAMS) },
                onSignOut = {
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }

        // Feature screens (will be navigated from homes later)
        composable(Routes.ADMISSIONS) { 
            AdmissionsScreen(
                onSubmit = { _, _ -> },
                onBack = { navController.popBackStack() },
                onStartNewAdmission = { navController.navigate(Routes.PERSONAL_DETAILS) },
                onViewPending = { /* Navigate to pending admissions */ },
                onSearchAdmissions = { /* Navigate to search */ }
            )
        }
        composable(Routes.STUDENTS) { 
            StudentsScreen(
                students = emptyList(),
                onOpen = { _ -> },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.STUDENT_LIST) {
            StudentsScreen(
                onBack = { navController.popBackStack() },
                onOpen = { studentId -> navController.navigate(Routes.STUDENT_DETAIL.replace("{studentId}", studentId)) }
            )
        }
        composable(Routes.STUDENT_DETAIL) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("studentId") ?: ""
            StudentDetailScreen(
                studentId = id,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PAYMENTS) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("studentId") ?: ""
            PaymentsScreen(
                studentId = id,
                onRecord = { _, _ -> },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HOSTEL) { 
            HostelScreen(
                onBack = { navController.popBackStack() },
                onViewHostels = { navController.navigate(Routes.HOSTEL_ROOMS) },
                onAllocateStudent = { navController.navigate(Routes.HOSTEL_ALLOCATION) }
            )
        }
        composable(Routes.HOSTEL_ROOMS) {
            HostelRoomsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HOSTEL_ALLOCATION) {
            HostelAllocationScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.STUDENT_DASHBOARD) {
            StudentDashboard(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToAttendance = { navController.navigate(Routes.ATTENDANCE) },
                onNavigateToMarks = { navController.navigate(Routes.VIEW_EXAM_RECORDS) },
                onNavigateToFees = { navController.navigate("payments/student123") },
                themeManager = themeManager,
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.ADMISSION_DASHBOARD) {
            AdmissionStaffDashboard(
                onNavigateToAdmissions = { navController.navigate(Routes.ADMISSIONS) },
                onNavigateToStudents = { navController.navigate(Routes.STUDENT_LIST) },
                onNavigateToPendingApplications = { navController.navigate(Routes.PENDING_APPLICATIONS) },
                onNavigateToReports = { navController.navigate(Routes.ADMISSION_REPORTS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                themeManager = themeManager,
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.HOSTEL_DASHBOARD) {
            HostelStaffDashboard(
                onNavigateToHostel = { navController.navigate(Routes.HOSTEL_ALLOCATION) },
                onNavigateToRooms = { navController.navigate(Routes.HOSTEL_ROOMS) },
                onNavigateToMaintenance = { navController.navigate(Routes.HOSTEL_MAINTENANCE) },
                onNavigateToReports = { navController.navigate(Routes.HOSTEL_REPORTS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                themeManager = themeManager,
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.ACCOUNTS_DASHBOARD) {
            AccountsStaffDashboard(
                onNavigateToPayments = { navController.navigate("payments/student123") },
                onNavigateToPaymentRecords = { navController.navigate(Routes.PAYMENT_RECORDS) },
                onNavigateToFinancialReports = { navController.navigate(Routes.FINANCIAL_REPORTS) },
                onNavigateToOutstandingDues = { navController.navigate(Routes.OUTSTANDING_DUES) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                themeManager = themeManager,
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.EXAM_DASHBOARD) {
            ExamStaffDashboard(
                onNavigateToExams = { navController.navigate(Routes.EXAMS) },
                onNavigateToMarks = { navController.navigate(Routes.ADD_UPDATE_MARKS) },
                onNavigateToResultAnalysis = { navController.navigate(Routes.RESULT_ANALYSIS) },
                onNavigateToTranscripts = { navController.navigate(Routes.TRANSCRIPTS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                themeManager = themeManager,
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.ADMIN_DASHBOARD) {
            AdminDashboard(
                onNavigateToAdmissions = { navController.navigate(Routes.ADMISSIONS) },
                onNavigateToStudents = { navController.navigate(Routes.STUDENT_LIST) },
                onNavigateToPayments = { navController.navigate("payments/student123") },
                onNavigateToHostel = { navController.navigate(Routes.HOSTEL_ALLOCATION) },
                onNavigateToExams = { navController.navigate(Routes.EXAMS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToNotifications = { navController.navigate(Routes.NOTIFICATIONS) },
                onNavigateToAnalytics = { navController.navigate(Routes.ANALYTICS) },
                onNavigateToDocuments = { navController.navigate(Routes.DOCUMENTS) },
                themeManager = themeManager,
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.DASHBOARD) { 
            DashboardScreen(
                onNavigateToPayments = { navController.navigate("payments/student123") },
                onNavigateToHostel = { navController.navigate(Routes.HOSTEL) },
                onNavigateToAdmissions = { navController.navigate(Routes.ADMISSIONS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToExams = { navController.navigate(Routes.EXAMS) },
                onNavigateToStudents = { navController.navigate(Routes.STUDENT_LIST) },
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.EXAMS) { 
            ExamScreen(
                onBack = { navController.popBackStack() },
                onViewExamRecords = { navController.navigate(Routes.VIEW_EXAM_RECORDS) },
                onAddUpdateMarks = { navController.navigate(Routes.ADD_UPDATE_MARKS) }
            )
        }
        composable(Routes.VIEW_EXAM_RECORDS) {
            ViewStudentExamRecordsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ADD_UPDATE_MARKS) {
            AddUpdateMarksScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PROFILE) { 
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate(Routes.PERSONAL_DETAILS) },
                onSignOut = {
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.PERSONAL_DETAILS) {
            PersonalDetailsScreen(
                onBack = { navController.popBackStack() },
                onNextStep = { /* Navigate to next step */ }
            )
        }
        composable(Routes.NOTIFICATIONS) {
            NotificationsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ANALYTICS) {
            AnalyticsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.DOCUMENTS) {
            DocumentsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ATTENDANCE) {
            AttendanceScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PENDING_APPLICATIONS) {
            PendingApplicationsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ADMISSION_REPORTS) {
            AdmissionReportsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HOSTEL_MAINTENANCE) {
            HostelMaintenanceScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HOSTEL_REPORTS) {
            HostelReportsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PAYMENT_RECORDS) {
            PaymentRecordsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.FINANCIAL_REPORTS) {
            FinancialReportsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.OUTSTANDING_DUES) {
            OutstandingDuesScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.RESULT_ANALYSIS) {
            ResultAnalysisScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.TRANSCRIPTS) {
            TranscriptsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private fun routeAllowed(viewModel: AuthViewModel, route: String): Boolean {
    // This function is not used in composition, so .value is acceptable here
    val role = viewModel.currentUser.value?.role ?: return false
    return RolePermissions.allowedRoutes(role).contains(route)
}
