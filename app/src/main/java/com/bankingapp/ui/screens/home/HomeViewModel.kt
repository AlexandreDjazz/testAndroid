package com.bankingapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankingapp.data.model.Transaction
import com.bankingapp.data.model.User
import com.bankingapp.data.repository.BankingRepository
import com.bankingapp.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val bankingRepository: BankingRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = userPreferencesRepository.currentUser
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val transactions: StateFlow<List<Transaction>> = bankingRepository.transactions
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
