package com.bankingapp.data.repository

import com.bankingapp.data.local.dao.ATMDao
import com.bankingapp.data.local.dao.TransactionDao
import com.bankingapp.data.local.toATM
import com.bankingapp.data.local.toEntity
import com.bankingapp.data.local.toTransaction
import com.bankingapp.data.model.ATM
import com.bankingapp.data.model.Transaction
import com.bankingapp.data.model.TransactionCategory
import com.bankingapp.data.model.TransactionStatus
import com.bankingapp.data.model.TransactionType
import com.bankingapp.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankingRepository @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val transactionDao: TransactionDao,
    private val atmDao: ATMDao
) {
    val transactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()
        .map { entities -> entities.map { it.toTransaction() } }

    init {
        // Initialize with sample data if database is empty
        CoroutineScope(Dispatchers.IO).launch {
            initializeSampleData()
        }
    }

    private suspend fun initializeSampleData() {
        // Check if we already have data
        val existingTransactions = transactionDao.getAllTransactions()
        var hasData = false
        existingTransactions.collect { list ->
            hasData = list.isNotEmpty()
        }

        if (!hasData) {
            // Insert sample transactions
            val sampleTransactions = generateSampleTransactions()
            transactionDao.insertTransactions(sampleTransactions.map { it.toEntity() })

            // Insert sample ATMs
            val sampleATMs = generateSampleATMs(48.8566, 2.3522)
            atmDao.insertATMs(sampleATMs.map { it.toEntity() })
        }
    }

    suspend fun makePayment(
        currentUser: User,
        recipient: String,
        amount: Double,
        category: TransactionCategory,
        description: String
    ): Result<Transaction> {
        return try {
            // Simulate network delay
            delay(1500)

            val newBalance = currentUser.balance - amount

            if (newBalance < 0) {
                return Result.failure(Exception("Insufficient funds"))
            }

            val transaction = Transaction(
                id = UUID.randomUUID().toString(),
                type = TransactionType.DEBIT,
                amount = amount,
                recipient = recipient,
                category = category,
                description = description,
                status = TransactionStatus.COMPLETED
            )

            // Save transaction to database
            transactionDao.insertTransaction(transaction.toEntity())

            // Update user balance
            val updatedUser = currentUser.copy(balance = newBalance)
            userPreferencesRepository.updateUser(updatedUser)

            Result.success(transaction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTransactionsByCategory(): Map<TransactionCategory, Double> {
        val allTransactions = mutableListOf<Transaction>()
        transactions.collect { list ->
            allTransactions.addAll(list)
        }

        return allTransactions
            .filter { it.type == TransactionType.DEBIT }
            .groupBy { it.category }
            .mapValues { entry ->
                entry.value.sumOf { it.amount }
            }
    }

    suspend fun getNearbyATMs(latitude: Double, longitude: Double): List<ATM> {
        var atmList = listOf<ATM>()
        atmDao.getAllATMs().collect { entities ->
            atmList = entities.map { it.toATM() }
        }

        // If no ATMs in database, generate and save sample ones
        if (atmList.isEmpty()) {
            val sampleATMs = generateSampleATMs(latitude, longitude)
            atmDao.insertATMs(sampleATMs.map { it.toEntity() })
            return sampleATMs
        }

        return atmList
    }

    private fun generateSampleATMs(latitude: Double, longitude: Double): List<ATM> {
        return listOf(
            ATM(
                id = "1",
                name = "ATM - Centre Ville",
                address = "123 Rue de la République",
                latitude = latitude + 0.001,
                longitude = longitude + 0.001,
                isAvailable = true,
                distance = 0.15
            ),
            ATM(
                id = "2",
                name = "ATM - Gare",
                address = "45 Avenue de la Gare",
                latitude = latitude - 0.002,
                longitude = longitude + 0.003,
                isAvailable = true,
                distance = 0.35
            ),
            ATM(
                id = "3",
                name = "ATM - Commerce",
                address = "78 Boulevard du Commerce",
                latitude = latitude + 0.003,
                longitude = longitude - 0.002,
                isAvailable = false,
                distance = 0.42
            ),
            ATM(
                id = "4",
                name = "ATM - Université",
                address = "90 Rue de l'Université",
                latitude = latitude - 0.004,
                longitude = longitude - 0.001,
                isAvailable = true,
                distance = 0.58
            ),
            ATM(
                id = "5",
                name = "ATM - Marché",
                address = "12 Place du Marché",
                latitude = latitude + 0.005,
                longitude = longitude + 0.004,
                isAvailable = true,
                distance = 0.71
            )
        )
    }

    private fun generateSampleTransactions(): List<Transaction> {
        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L

        return listOf(
            Transaction(
                id = UUID.randomUUID().toString(),
                type = TransactionType.CREDIT,
                amount = 3500.0,
                recipient = "Salary Deposit",
                category = TransactionCategory.SALARY,
                description = "Monthly Salary",
                timestamp = now - oneDay * 3,
                status = TransactionStatus.COMPLETED
            ),
            Transaction(
                id = UUID.randomUUID().toString(),
                type = TransactionType.DEBIT,
                amount = 45.50,
                recipient = "Restaurant Le Gourmet",
                category = TransactionCategory.FOOD,
                description = "Dinner",
                timestamp = now - oneDay * 2,
                status = TransactionStatus.COMPLETED
            ),
            Transaction(
                id = UUID.randomUUID().toString(),
                type = TransactionType.DEBIT,
                amount = 120.0,
                recipient = "Supermarché",
                category = TransactionCategory.SHOPPING,
                description = "Groceries",
                timestamp = now - oneDay * 2,
                status = TransactionStatus.COMPLETED
            ),
            Transaction(
                id = UUID.randomUUID().toString(),
                type = TransactionType.DEBIT,
                amount = 25.0,
                recipient = "Metro Pass",
                category = TransactionCategory.TRANSPORT,
                description = "Monthly pass",
                timestamp = now - oneDay * 1,
                status = TransactionStatus.COMPLETED
            ),
            Transaction(
                id = UUID.randomUUID().toString(),
                type = TransactionType.DEBIT,
                amount = 89.99,
                recipient = "Electric Company",
                category = TransactionCategory.BILLS,
                description = "Electricity bill",
                timestamp = now - oneDay * 1,
                status = TransactionStatus.COMPLETED
            ),
            Transaction(
                id = UUID.randomUUID().toString(),
                type = TransactionType.DEBIT,
                amount = 15.0,
                recipient = "Netflix",
                category = TransactionCategory.ENTERTAINMENT,
                description = "Subscription",
                timestamp = now,
                status = TransactionStatus.COMPLETED
            )
        )
    }
}
