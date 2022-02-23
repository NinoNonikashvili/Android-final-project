package com.example.bankapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.repositories.CurrencyRepository
import com.example.bankapp.util.ApiState
import com.example.bankapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) :
    ViewModel() {

    private val _calculateStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val calculateStateFlow: StateFlow<ApiState> = _calculateStateFlow


    fun convert(
        amount: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        viewModelScope.launch {
            _calculateStateFlow.value = ApiState.Loading
            val resultRates =
                currencyRepository.getConvertInfo(amount, fromCurrency, toCurrency)

            when (resultRates) {
                is Resource.Error -> {
                    _calculateStateFlow.value =
                        ApiState.Failure(resultRates.message!!)
                }
                is Resource.Success -> {
                    _calculateStateFlow.value = ApiState.Success(resultRates.data)
                }
            }
        }

    }
}