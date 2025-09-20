package com.example.collegeerp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeerp.domain.model.AppUser
import com.example.collegeerp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser: StateFlow<AppUser?> = authRepository.currentUserFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun signIn(email: String, password: String, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) {
        viewModelScope.launch {
            try {
                android.util.Log.d("AuthViewModel", "Starting sign in for: $email")
                authRepository.signIn(email, password)
                android.util.Log.d("AuthViewModel", "Sign in successful")
                onComplete(true, null)
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Sign-in failed for $email", e)
                val errorMessage = when {
                    e.message?.contains("network", ignoreCase = true) == true -> "Network error. Please check your internet connection."
                    e.message?.contains("password", ignoreCase = true) == true -> "Invalid email or password."
                    e.message?.contains("user-not-found", ignoreCase = true) == true -> "No account found with this email."
                    e.message?.contains("invalid-email", ignoreCase = true) == true -> "Invalid email format."
                    e.message?.contains("too-many-requests", ignoreCase = true) == true -> "Too many failed attempts. Please try again later."
                    else -> e.message ?: "Sign in failed. Please try again."
                }
                onComplete(false, errorMessage)
            }
        }
    }
    
    fun signUp(email: String, password: String, name: String, role: String = "Student", onComplete: (Boolean, String?) -> Unit = { _, _ -> }) {
        viewModelScope.launch { 
            try {
                android.util.Log.d("AuthViewModel", "Starting signup for email: $email with role: $role")
                authRepository.signUp(email, password, name, role)
                android.util.Log.d("AuthViewModel", "Signup successful")
                onComplete(true, null)
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Signup failed for email: $email", e)
                val errorMessage = when {
                    e.message?.contains("email-already-in-use", ignoreCase = true) == true -> "An account with this email already exists."
                    e.message?.contains("weak-password", ignoreCase = true) == true -> "Password is too weak. Please choose a stronger password."
                    e.message?.contains("invalid-email", ignoreCase = true) == true -> "Invalid email format."
                    e.message?.contains("network", ignoreCase = true) == true -> "Network error. Please check your internet connection."
                    else -> e.message ?: "Account creation failed. Please try again."
                }
                onComplete(false, errorMessage)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch { 
            try {
                android.util.Log.d("AuthViewModel", "Starting sign out")
                authRepository.signOut()
                android.util.Log.d("AuthViewModel", "Sign out successful")
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Sign out failed", e)
            }
        }
    }
}


