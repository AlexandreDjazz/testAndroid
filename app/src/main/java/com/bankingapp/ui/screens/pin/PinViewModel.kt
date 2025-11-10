package com.bankingapp.ui.screens.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankingapp.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _pinState = MutableStateFlow<PinState>(PinState.Creating)
    val pinState: StateFlow<PinState> = _pinState.asStateFlow()

    private val _currentPin = MutableStateFlow("")
    val currentPin: StateFlow<String> = _currentPin.asStateFlow()

    private var tempPin: String? = null

    val hasPin: StateFlow<Boolean> = userPreferencesRepository.hasPin
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val savedPin: StateFlow<String?> = userPreferencesRepository.currentUser
        .map { it?.pin }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            hasPin.collect { hasPinSet ->
                if (hasPinSet) {
                    _pinState.value = PinState.Verifying
                } else {
                    _pinState.value = PinState.Creating
                }
            }
        }
    }

    fun addDigit(digit: Int) {
        if (_currentPin.value.length < 4) {
            _currentPin.value += digit.toString()

            if (_currentPin.value.length == 4) {
                handlePinComplete()
            }
        }
    }

    fun removeDigit() {
        if (_currentPin.value.isNotEmpty()) {
            _currentPin.value = _currentPin.value.dropLast(1)
        }
    }

    private fun handlePinComplete() {
        viewModelScope.launch {
            when (_pinState.value) {
                is PinState.Creating -> {
                    tempPin = _currentPin.value
                    _pinState.value = PinState.Confirming
                    _currentPin.value = ""
                }
                is PinState.Confirming -> {
                    if (_currentPin.value == tempPin) {
                        // Save the PIN
                        userPreferencesRepository.currentUser.collect { user ->
                            if (user != null) {
                                val updatedUser = user.copy(pin = _currentPin.value)
                                userPreferencesRepository.saveUser(updatedUser)
                                _pinState.value = PinState.Success
                            }
                        }
                    } else {
                        _pinState.value = PinState.Error("PINs do not match")
                        kotlinx.coroutines.delay(1500)
                        _pinState.value = PinState.Creating
                        _currentPin.value = ""
                        tempPin = null
                    }
                }
                is PinState.Verifying -> {
                    val savedPinValue = savedPin.value
                    if (_currentPin.value == savedPinValue) {
                        _pinState.value = PinState.Success
                    } else {
                        _pinState.value = PinState.Error("Incorrect PIN")
                        kotlinx.coroutines.delay(1500)
                        _currentPin.value = ""
                        _pinState.value = PinState.Verifying
                    }
                }
                else -> {}
            }
        }
    }

    fun resetState() {
        _currentPin.value = ""
        _pinState.value = if (hasPin.value) PinState.Verifying else PinState.Creating
        tempPin = null
    }
}

sealed class PinState {
    object Creating : PinState()
    object Confirming : PinState()
    object Verifying : PinState()
    object Success : PinState()
    data class Error(val message: String) : PinState()
}
