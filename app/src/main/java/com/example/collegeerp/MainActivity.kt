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
                        UserRole.STAFF -> navController.navigate(Routes.STAFF_HOME) { popUpTo(Routes.AUTH) { inclusive = true } }
                        UserRole.STUDENT -> navController.navigate(Routes.STUDENT_HOME) { popUpTo(Routes.AUTH) { inclusive = true } }
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
        composable(Routes.ADMISSIONS) { AdmissionsScreen { _, _ -> } }
        composable(Routes.STUDENTS) { StudentsScreen(students = emptyList()) { _ -> } }
        composable(Routes.STUDENT_DETAIL) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("studentId") ?: ""
            StudentDetailScreen(id)
        }
        composable(Routes.PAYMENTS) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("studentId") ?: ""
            PaymentsScreen(id) { _, _ -> }
        }
        composable(Routes.HOSTEL) { HostelScreen() }
        composable(Routes.DASHBOARD) { 
            DashboardScreen(
                onNavigateToPayments = { navController.navigate(Routes.PAYMENTS) },
                onNavigateToHostel = { navController.navigate(Routes.HOSTEL) },
                onNavigateToAdmissions = { navController.navigate(Routes.ADMISSIONS) }
            )
        }
        composable(Routes.EXAMS) { ExamScreen() }
        composable(Routes.PROFILE) { 
            ProfileScreen(
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
