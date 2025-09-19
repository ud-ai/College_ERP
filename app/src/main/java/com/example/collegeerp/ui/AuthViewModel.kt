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
                authRepository.signIn(email, password)
                onComplete(true, null)
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Sign-in failed", e)
                onComplete(false, e.message ?: "Sign in failed")
            }
        }
    }
    
    fun signUp(email: String, password: String, name: String, role: String = "Student", onComplete: (Boolean) -> Unit = {}) {
        viewModelScope.launch { 
            try {
                android.util.Log.d("AuthViewModel", "Starting signup for email: $email with role: $role")
                authRepository.signUp(email, password, name, role)
                android.util.Log.d("AuthViewModel", "Signup successful")
                onComplete(true)
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Signup failed for email: $email", e)
                onComplete(false)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch { authRepository.signOut() }
    }
}


