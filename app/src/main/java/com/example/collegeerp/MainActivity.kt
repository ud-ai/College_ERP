package com.example.collegeerp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeerp.domain.model.UserRole
import com.example.collegeerp.ui.AuthViewModel
import com.example.collegeerp.ui.navigation.Routes
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
import dagger.hilt.android.AndroidEntryPoint

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

    NavHost(navController = navController, startDestination = Routes.AUTH) {
        composable(Routes.AUTH) {
            AuthScreen(onSignIn = { email, password -> viewModel.signIn(email, password) })
            val user = viewModel.currentUser.value
            if (user != null) {
                when (user.role) {
                    UserRole.ADMIN -> navController.navigate(Routes.ADMIN_HOME) { popUpTo(Routes.AUTH) { inclusive = true } }
                    UserRole.STAFF -> navController.navigate(Routes.STAFF_HOME) { popUpTo(Routes.AUTH) { inclusive = true } }
                    UserRole.STUDENT -> navController.navigate(Routes.STUDENT_HOME) { popUpTo(Routes.AUTH) { inclusive = true } }
                }
            }
        }
        composable(Routes.ADMIN_HOME) { AdminHome(onNavigate = { route -> navController.navigate(route) }) }
        composable(Routes.STAFF_HOME) { StaffHome(onNavigate = { route -> navController.navigate(route) }) }
        composable(Routes.STUDENT_HOME) { StudentHome() }

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
        composable(Routes.DASHBOARD) { DashboardScreen() }
    }
}
