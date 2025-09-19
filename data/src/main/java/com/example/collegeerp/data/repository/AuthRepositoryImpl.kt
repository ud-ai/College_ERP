package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.UserEntity
import com.example.collegeerp.data.util.await
import com.example.collegeerp.domain.model.AppUser
import com.example.collegeerp.domain.model.UserRole
import com.example.collegeerp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: AppDatabase
) : AuthRepository {

    override val currentUserFlow: Flow<AppUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }.flatMapLatest { user ->
        if (user == null) flowOf(null) else flow {
            try {
                val tokenResult = user.getIdToken(true).await()
                val roles = (tokenResult.claims?.get("roles") as? List<*>)?.map { it.toString() } ?: emptyList()
                val role = when {
                    roles.contains("ADMIN") -> UserRole.ADMIN
                    roles.contains("STAFF") -> UserRole.STAFF
                    else -> UserRole.STUDENT
                }
                emit(AppUser(uid = user.uid, name = user.displayName ?: "", email = user.email ?: "", role = role, phone = user.phoneNumber, createdAt = System.currentTimeMillis()))
            } catch (e: Exception) {
                android.util.Log.e("AuthRepositoryImpl", "Error fetching ID token: ${e.message}", e)
                emit(AppUser(uid = user.uid, name = user.displayName ?: "", email = user.email ?: "", role = UserRole.STUDENT, phone = user.phoneNumber, createdAt = System.currentTimeMillis()))
            }
        }
    }

    override suspend fun signIn(email: String, password: String) {
        try {
            android.util.Log.d("AuthRepository", "Attempting sign in for: $email")
            auth.signInWithEmailAndPassword(email, password).await()
            android.util.Log.d("AuthRepository", "Sign in successful")
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Sign in failed: ${e.message}", e)
            throw e
        }
    }
    
    override suspend fun signUp(email: String, password: String, name: String): String {
        try {
            android.util.Log.d("AuthRepositoryImpl", "Creating user with email: $email")
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw IllegalStateException("User creation failed - Firebase returned null user")
            
            android.util.Log.d("AuthRepositoryImpl", "User created successfully, updating profile")
            // Update display name
            val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            user.updateProfile(profileUpdates).await()
            
            android.util.Log.d("AuthRepositoryImpl", "Profile updated successfully for uid: ${user.uid}")
            return user.uid
        } catch (e: Exception) {
            android.util.Log.e("AuthRepositoryImpl", "Error during signup for email: $email - ${e.message}", e)
            throw e
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}

// Extension to convert Task to suspend without adding extra dependencies
// Using the shared Firebase Task extension from util package


