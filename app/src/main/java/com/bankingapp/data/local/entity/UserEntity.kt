package com.bankingapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val email: String,
    val pin: String? = null,
    val accountNumber: String,
    val balance: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
