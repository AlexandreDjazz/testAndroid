package com.bankingapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val type: String, // DEBIT or CREDIT
    val amount: Double,
    val recipient: String,
    val category: String, // Category name
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "COMPLETED" // PENDING, COMPLETED, FAILED
)
