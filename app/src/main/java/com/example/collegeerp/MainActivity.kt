package com.example.collegeerp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.LaunchedEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MaterialTheme {
				Surface(color = MaterialTheme.colorScheme.background) {
					AppNav()
				}
			}
		}
	}
}

@Composable
fun AppNav(viewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val user by viewModel.currentUser.collectAsState()

    NavHost(navController = navController, startDestination = Routes.AUTH) {
        composable(Routes.AUTH) {
            AuthScreen(
                onSignIn = { email, password -> viewModel.signIn(email, password) }
            )
            
            // Handle navigation based on user role using LaunchedEffect to avoid navigating during composition
            LaunchedEffect(user) {
                val u = user
                if (u != null) {
                    when (u.role) {
                        UserRole.ADMIN -> navController.navigate(Routes.ADMIN_HOME) { popUpTo(Routes.AUTH) { inclusive = true } }
                        UserRole.STUDENT -> navController.navigate(Routes.STUDENT_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        UserRole.ADMISSION_CELL -> navController.navigate(Routes.ADMISSION_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        UserRole.HOSTEL_MANAGER -> navController.navigate(Routes.HOSTEL_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        UserRole.ACCOUNTS -> navController.navigate(Routes.ACCOUNTS_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
                        UserRole.EXAM_STAFF -> navController.navigate(Routes.EXAM_DASHBOARD) { popUpTo(Routes.AUTH) { inclusive = true } }
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
                onNavigateToAttendance = { /* Navigate to attendance */ },
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
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onSignOut = {
                    viewModel.signOut()
                    navController.navigate(Routes.AUTH) { popUpTo(0) }
                }
            )
        }
        composable(Routes.HOSTEL_DASHBOARD) {
            HostelStaffDashboard(
                onNavigateToHostel = { navController.navigate(Routes.HOSTEL) },
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
    }
}

private fun routeAllowed(viewModel: AuthViewModel, route: String): Boolean {
    // This function is not used in composition, so .value is acceptable here
    val role = viewModel.currentUser.value?.role ?: return false
    return RolePermissions.allowedRoutes(role).contains(route)
}
