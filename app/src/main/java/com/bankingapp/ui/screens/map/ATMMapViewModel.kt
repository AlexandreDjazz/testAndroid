package com.bankingapp.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankingapp.data.model.ATM
import com.bankingapp.data.repository.BankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ATMMapViewModel @Inject constructor(
    private val bankingRepository: BankingRepository
) : ViewModel() {

    private val _atmLocations = MutableStateFlow<List<ATM>>(emptyList())
    val atmLocations: StateFlow<List<ATM>> = _atmLocations.asStateFlow()

    private val _userLocation = MutableStateFlow<Pair<Double, Double>?>(null)
    val userLocation: StateFlow<Pair<Double, Double>?> = _userLocation.asStateFlow()

    fun loadNearbyATMs(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _userLocation.value = Pair(latitude, longitude)
            _atmLocations.value = bankingRepository.getNearbyATMs(latitude, longitude)
        }
    }

    fun setUserLocation(latitude: Double, longitude: Double) {
        _userLocation.value = Pair(latitude, longitude)
    }
}
