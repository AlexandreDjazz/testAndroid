package com.bankingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bankingapp.data.local.dao.ATMDao
import com.bankingapp.data.local.dao.TransactionDao
import com.bankingapp.data.local.dao.UserDao
import com.bankingapp.data.local.entity.ATMEntity
import com.bankingapp.data.local.entity.TransactionEntity
import com.bankingapp.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        TransactionEntity::class,
        ATMEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BankingDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun atmDao(): ATMDao

    companion object {
        const val DATABASE_NAME = "banking_database"
    }
}
