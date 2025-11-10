package com.bankingapp.ui.screens.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankingapp.data.model.Transaction
import com.bankingapp.data.model.TransactionCategory
import com.bankingapp.data.model.User
import com.bankingapp.data.repository.BankingRepository
import com.bankingapp.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val bankingRepository: BankingRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = userPreferencesRepository.currentUser
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val paymentState: StateFlow<PaymentState> = _paymentState.asStateFlow()

    fun makePayment(
        recipient: String,
        amount: String,
        category: TransactionCategory,
        description: String
    ) {
        viewModelScope.launch {
            val user = currentUser.value ?: return@launch

            if (recipient.isEmpty() || amount.isEmpty()) {
                _paymentState.value = PaymentState.Error("Please fill in all fields")
                return@launch
            }

            val amountDouble = amount.toDoubleOrNull()
            if (amountDouble == null || amountDouble <= 0) {
                _paymentState.value = PaymentState.Error("Invalid amount")
                return@launch
            }

            if (amountDouble > user.balance) {
                _paymentState.value = PaymentState.Error("Insufficient funds")
                return@launch
            }

            _paymentState.value = PaymentState.Loading

            val result = bankingRepository.makePayment(
                currentUser = user,
                recipient = recipient,
                amount = amountDouble,
                category = category,
                description = description
            )

            result.fold(
                onSuccess = { transaction ->
                    _paymentState.value = PaymentState.Success(transaction)
                },
                onFailure = { error ->
                    _paymentState.value = PaymentState.Error(error.message ?: "Payment failed")
                }
            )
        }
    }

    fun resetState() {
        _paymentState.value = PaymentState.Idle
    }
}

sealed class PaymentState {
    object Idle : PaymentState()
    object Loading : PaymentState()
    data class Success(val transaction: Transaction) : PaymentState()
    data class Error(val message: String) : PaymentState()
}
