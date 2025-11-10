package com.bankingapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atms")
data class ATMEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val isAvailable: Boolean = true,
    val distance: Double = 0.0
)
