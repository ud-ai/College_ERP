package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.UserEntity
import com.example.collegeerp.domain.model.AppUser
import com.example.collegeerp.domain.model.UserRole
import com.example.collegeerp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
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
    }.map { user ->
        user?.let {
            val roles = (it.getIdToken(false).result?.claims?.get("roles") as? List<*>)?.map { r -> r.toString() }
            val role = when {
                roles?.contains("ADMIN") == true -> UserRole.ADMIN
                roles?.contains("STAFF") == true -> UserRole.STAFF
                else -> UserRole.STUDENT
            }
            AppUser(uid = it.uid, name = it.displayName ?: "", email = it.email ?: "", role = role, phone = it.phoneNumber, createdAt = System.currentTimeMillis())
        }
    }

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}

// Extension to convert Task to suspend without adding extra dependencies
private suspend fun <T> com.google.android.gms.tasks.Task<T>.await(): T {
    return kotlinx.coroutines.suspendCancellableCoroutine { cont ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) cont.resume(task.result, null)
            else cont.resumeWithException(task.exception ?: RuntimeException("Task failed"))
        }
    }
}


