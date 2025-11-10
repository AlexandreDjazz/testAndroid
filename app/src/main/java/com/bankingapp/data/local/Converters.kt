package com.bankingapp.data.local

import com.bankingapp.data.local.entity.ATMEntity
import com.bankingapp.data.local.entity.TransactionEntity
import com.bankingapp.data.local.entity.UserEntity
import com.bankingapp.data.model.ATM
import com.bankingapp.data.model.Transaction
import com.bankingapp.data.model.TransactionCategory
import com.bankingapp.data.model.TransactionStatus
import com.bankingapp.data.model.TransactionType
import com.bankingapp.data.model.User

// Converters between Entity and Model

fun UserEntity.toUser() = User(
    id = id,
    fullName = fullName,
    email = email,
    pin = pin,
    accountNumber = accountNumber,
    balance = balance,
    createdAt = createdAt
)

fun User.toEntity() = UserEntity(
    id = id,
    fullName = fullName,
    email = email,
    pin = pin,
    accountNumber = accountNumber,
    balance = balance,
    createdAt = createdAt
)

fun TransactionEntity.toTransaction() = Transaction(
    id = id,
    type = TransactionType.valueOf(type),
    amount = amount,
    recipient = recipient,
    category = TransactionCategory.values().find { it.name == category } ?: TransactionCategory.OTHER,
    description = description,
    timestamp = timestamp,
    status = TransactionStatus.valueOf(status)
)

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    type = type.name,
    amount = amount,
    recipient = recipient,
    category = category.name,
    description = description,
    timestamp = timestamp,
    status = status.name
)

fun ATMEntity.toATM() = ATM(
    id = id,
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
    isAvailable = isAvailable,
    distance = distance
)

fun ATM.toEntity() = ATMEntity(
    id = id,
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
    isAvailable = isAvailable,
    distance = distance
)
