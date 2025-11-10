package com.bankingapp.data.local.dao

import androidx.room.*
import com.bankingapp.data.local.entity.ATMEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ATMDao {
    @Query("SELECT * FROM atms")
    fun getAllATMs(): Flow<List<ATMEntity>>

    @Query("SELECT * FROM atms WHERE id = :atmId")
    suspend fun getATMById(atmId: String): ATMEntity?

    @Query("SELECT * FROM atms WHERE isAvailable = 1")
    fun getAvailableATMs(): Flow<List<ATMEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertATM(atm: ATMEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertATMs(atms: List<ATMEntity>)

    @Update
    suspend fun updateATM(atm: ATMEntity)

    @Delete
    suspend fun deleteATM(atm: ATMEntity)

    @Query("DELETE FROM atms")
    suspend fun deleteAllATMs()
}
