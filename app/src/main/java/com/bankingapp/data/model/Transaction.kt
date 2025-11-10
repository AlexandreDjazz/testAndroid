package com.bankingapp.data.model

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val recipient: String,
    val category: TransactionCategory,
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: TransactionStatus = TransactionStatus.COMPLETED
)

enum class TransactionType {
    DEBIT, CREDIT
}

enum class TransactionStatus {
    PENDING, COMPLETED, FAILED
}

enum class TransactionCategory(val displayName: String, val color: Long) {
    FOOD("Food & Dining", 0xFFFF6B6B),
    TRANSPORT("Transport", 0xFF4ECDC4),
    SHOPPING("Shopping", 0xFFFFE66D),
    ENTERTAINMENT("Entertainment", 0xFFA8E6CF),
    BILLS("Bills & Utilities", 0xFF95E1D3),
    SALARY("Salary", 0xFF38B6FF),
    OTHER("Other", 0xFFB8B8B8)
}
