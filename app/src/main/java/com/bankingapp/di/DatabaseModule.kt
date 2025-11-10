package com.bankingapp.di

import android.content.Context
import androidx.room.Room
import com.bankingapp.data.local.BankingDatabase
import com.bankingapp.data.local.dao.ATMDao
import com.bankingapp.data.local.dao.TransactionDao
import com.bankingapp.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBankingDatabase(@ApplicationContext context: Context): BankingDatabase {
        return Room.databaseBuilder(
            context,
            BankingDatabase::class.java,
            BankingDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: BankingDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: BankingDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideATMDao(database: BankingDatabase): ATMDao {
        return database.atmDao()
    }
}
