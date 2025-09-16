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
import com.example.collegeerp.ui.screens.AdminHome
import com.example.collegeerp.ui.screens.AuthScreen
import com.example.collegeerp.ui.screens.StaffHome
import com.example.collegeerp.ui.screens.StudentHome
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

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            AuthScreen(onSignIn = { email, password -> viewModel.signIn(email, password) })
            val user = viewModel.currentUser.value
            if (user != null) {
                when (user.role) {
                    UserRole.ADMIN -> navController.navigate("admin") { popUpTo("auth") { inclusive = true } }
                    UserRole.STAFF -> navController.navigate("staff") { popUpTo("auth") { inclusive = true } }
                    UserRole.STUDENT -> navController.navigate("student") { popUpTo("auth") { inclusive = true } }
                }
            }
        }
        composable("admin") { AdminHome() }
        composable("staff") { StaffHome() }
        composable("student") { StudentHome() }
    }
}
