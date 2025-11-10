package com.bankingapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankingapp.data.model.User
import com.bankingapp.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // Simulate network delay
            kotlinx.coroutines.delay(1000)

            // For demo purposes, accept any email/password
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(
                    id = UUID.randomUUID().toString(),
                    fullName = "Demo User",
                    email = email,
                    accountNumber = generateAccountNumber(),
                    balance = 5000.0
                )
                userPreferencesRepository.saveUser(user)
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Please fill in all fields")
            }
        }
    }

    fun signup(fullName: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            when {
                fullName.isEmpty() || email.isEmpty() || password.isEmpty() -> {
                    _authState.value = AuthState.Error("Please fill in all fields")
                }
                password != confirmPassword -> {
                    _authState.value = AuthState.Error("Passwords do not match")
                }
                password.length < 6 -> {
                    _authState.value = AuthState.Error("Password must be at least 6 characters")
                }
                else -> {
                    // Simulate network delay
                    kotlinx.coroutines.delay(1000)

                    val user = User(
                        id = UUID.randomUUID().toString(),
                        fullName = fullName,
                        email = email,
                        accountNumber = generateAccountNumber(),
                        balance = 1000.0 // Welcome bonus
                    )
                    userPreferencesRepository.saveUser(user)
                    _authState.value = AuthState.Success
                }
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    private fun generateAccountNumber(): String {
        return "ACC${(100000000..999999999).random()}"
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
