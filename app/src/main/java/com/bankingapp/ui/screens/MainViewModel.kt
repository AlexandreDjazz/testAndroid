package com.bankingapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankingapp.data.model.User
import com.bankingapp.data.repository.BankingRepository
import com.bankingapp.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val bankingRepository: BankingRepository
) : ViewModel() {

    val isLoggedIn: Flow<Boolean> = userPreferencesRepository.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val hasPin: Flow<Boolean> = userPreferencesRepository.hasPin
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val currentUser: Flow<User?> = userPreferencesRepository.currentUser
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val isDarkMode: Flow<Boolean> = userPreferencesRepository.isDarkMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkMode(enabled)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.logout()
        }
    }
}
