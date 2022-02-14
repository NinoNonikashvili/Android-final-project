package com.example.bankapp.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.util.ApiState
import com.example.bankapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(private val currencyRepository: CurrencyRepository) :
    ViewModel() {

    private val _convertStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val convertStateFlow: StateFlow<ApiState> = _convertStateFlow


    fun convert(
        amount: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        viewModelScope.launch {
            _convertStateFlow.value = ApiState.Loading
            val resultRates =
                currencyRepository.getConvertInfo(amount, fromCurrency, toCurrency)

            when (resultRates) {
                is Resource.Error -> {
                    _convertStateFlow.value =
                        ApiState.Failure(resultRates.message!!)
                }
                is Resource.Success -> {
                    _convertStateFlow.value = ApiState.Success(resultRates.data)
                }
            }
        }

    }
}



