package com.bankingapp.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankingapp.data.model.TransactionCategory
import com.bankingapp.data.repository.BankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val bankingRepository: BankingRepository
) : ViewModel() {

    private val _spendingByCategory = MutableStateFlow<Map<TransactionCategory, Double>>(emptyMap())
    val spendingByCategory: StateFlow<Map<TransactionCategory, Double>> = _spendingByCategory.asStateFlow()

    init {
        loadSpendingData()
    }

    private fun loadSpendingData() {
        viewModelScope.launch {
            _spendingByCategory.value = bankingRepository.getTransactionsByCategory()
        }
    }

    fun getTotalSpending(): Double {
        return _spendingByCategory.value.values.sum()
    }
}
