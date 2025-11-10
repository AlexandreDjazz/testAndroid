package com.bankingapp.data.model

data class ATM(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val isAvailable: Boolean = true,
    val distance: Double = 0.0
)
