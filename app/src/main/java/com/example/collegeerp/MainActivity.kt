package com.example.collegeerp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.collegeerp.ui.theme.CollegeERPTheme
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
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			var isDarkMode by remember { mutableStateOf(false) }
			CollegeERPTheme(darkTheme = isDarkMode) {
				Surface(color = MaterialTheme.colorScheme.background) {
					AppNav(isDarkMode = isDarkMode, onThemeToggle = { isDarkMode = it })
				}
			}
		}
	}
}

@Composable
fun AppNav(
    isDarkMode: Boolean = false,
    onThemeToggle: (Boolean) -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val user by viewModel.currentUser.collectAsState()

    NavHost(navController = navController, startDestination = Routes.AUTH) {
        composable(Routes.AUTH) {
            AuthScreen(
                onSignIn = { email, password -> viewModel.signIn(email, password) },
                isDarkMode = isDarkMode,
                onThemeToggle = onThemeToggle
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
            PaymentsScreen(id) { _, _ -> }
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
                onNavigateToFees = { navController.navigate(Routes.PAYMENTS) },
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
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.ACCOUNTS_DASHBOARD) {
            AccountsStaffDashboard(
                onNavigateToPayments = { navController.navigate(Routes.PAYMENTS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
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
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
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
                onNavigateToPayments = { navController.navigate(Routes.PAYMENTS) },
                onNavigateToHostel = { navController.navigate(Routes.HOSTEL_ALLOCATION) },
                onNavigateToExams = { navController.navigate(Routes.EXAMS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToNotifications = { navController.navigate(Routes.NOTIFICATIONS) },
                onNavigateToAnalytics = { navController.navigate(Routes.ANALYTICS) },
                onNavigateToDocuments = { navController.navigate(Routes.DOCUMENTS) },
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.DASHBOARD) { 
            DashboardScreen(
                onNavigateToPayments = { navController.navigate(Routes.PAYMENTS) },
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
    }
}

private fun routeAllowed(viewModel: AuthViewModel, route: String): Boolean {
    // This function is not used in composition, so .value is acceptable here
    val role = viewModel.currentUser.value?.role ?: return false
    return RolePermissions.allowedRoutes(role).contains(route)
}
