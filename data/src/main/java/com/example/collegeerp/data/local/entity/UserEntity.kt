package com.example.collegeerp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.collegeerp.domain.model.AppUser
import com.example.collegeerp.domain.model.UserRole

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String?,
    val createdAt: Long
)

fun UserEntity.toDomain(): AppUser = AppUser(
    uid = uid,
    name = name,
    email = email,
    role = UserRole.valueOf(role),
    phone = phone,
    createdAt = createdAt
)

fun AppUser.toEntity(): UserEntity = UserEntity(
    uid = uid,
    name = name,
    email = email,
    role = role.name,
    phone = phone,
    createdAt = createdAt
)