package com.bankingapp.data.model

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val pin: String? = null,
    val accountNumber: String,
    val balance: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
