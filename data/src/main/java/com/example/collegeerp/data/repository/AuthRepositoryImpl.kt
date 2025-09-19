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
                // First try to get role from local database
                val localUser = db.userDao().getUserByUid(user.uid)
                val role = if (localUser != null) {
                    try {
                        UserRole.valueOf(localUser.role)
                    } catch (e: Exception) {
                        UserRole.STUDENT
                    }
                } else {
                    // Fallback to Firebase custom claims
                    try {
                        val tokenResult = user.getIdToken(true).await()
                        val roles = (tokenResult.claims?.get("roles") as? List<*>)?.map { it.toString() } ?: emptyList()
                        when {
                            roles.contains("ADMIN") -> UserRole.ADMIN
                            roles.contains("ADMISSION_CELL") -> UserRole.ADMISSION_CELL
                            roles.contains("ACCOUNTS") -> UserRole.ACCOUNTS
                            roles.contains("HOSTEL_MANAGER") -> UserRole.HOSTEL_MANAGER
                            roles.contains("EXAM_STAFF") -> UserRole.EXAM_STAFF
                            else -> UserRole.STUDENT
                        }
                    } catch (e: Exception) {
                        android.util.Log.e("AuthRepositoryImpl", "Error fetching ID token: ${e.message}", e)
                        UserRole.STUDENT
                    }
                }
                
                emit(AppUser(
                    uid = user.uid, 
                    name = user.displayName ?: localUser?.name ?: "", 
                    email = user.email ?: "", 
                    role = role, 
                    phone = user.phoneNumber ?: localUser?.phone, 
                    createdAt = localUser?.createdAt ?: System.currentTimeMillis()
                ))
            } catch (e: Exception) {
                android.util.Log.e("AuthRepositoryImpl", "Error in currentUserFlow: ${e.message}", e)
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
    
    override suspend fun signUp(email: String, password: String, name: String, role: String): String {
        try {
            android.util.Log.d("AuthRepositoryImpl", "Creating user with email: $email and role: $role")
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw IllegalStateException("User creation failed - Firebase returned null user")
            
            android.util.Log.d("AuthRepositoryImpl", "User created successfully, updating profile")
            // Update display name
            val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            user.updateProfile(profileUpdates).await()
            
            // Store user role in local database for now
            val userRole = mapStringToUserRole(role)
            val userEntity = UserEntity(
                uid = user.uid,
                name = name,
                email = email,
                role = userRole.name,
                phone = null,
                createdAt = System.currentTimeMillis()
            )
            db.userDao().insertUser(userEntity)
            
            android.util.Log.d("AuthRepositoryImpl", "User role assigned: $role (${userRole.name}) for uid: ${user.uid}")
            return user.uid
        } catch (e: Exception) {
            android.util.Log.e("AuthRepositoryImpl", "Error during signup for email: $email - ${e.message}", e)
            throw e
        }
    }
    
    private fun mapStringToUserRole(roleString: String): UserRole {
        return when (roleString) {
            "Student" -> UserRole.STUDENT
            "Admission Cell" -> UserRole.ADMISSION_CELL
            "Accounts" -> UserRole.ACCOUNTS
            "Hostel Manager" -> UserRole.HOSTEL_MANAGER
            "Exam Staff" -> UserRole.EXAM_STAFF
            "Admin" -> UserRole.ADMIN
            else -> UserRole.STUDENT
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}

// Extension to convert Task to suspend without adding extra dependencies
// Using the shared Firebase Task extension from util package


