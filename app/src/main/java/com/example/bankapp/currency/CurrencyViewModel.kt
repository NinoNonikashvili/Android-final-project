package com.example.bankapp.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.model.Rate
import com.example.bankapp.util.ApiState
import com.example.bankapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(private val coursesRepository: CurrencyRepository) :
    ViewModel() {

    private val _currencyStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val currencyStateFlow: StateFlow<ApiState> = _currencyStateFlow

    fun getCurrency() {
        viewModelScope.launch {
            _currencyStateFlow.value = ApiState.Loading
            val resultOff = async { coursesRepository.getOfficialRates() }
            val resultComm = async { coursesRepository.getCommercialRates() }

            try {
                if (resultOff.await() is Resource.Success && resultComm.await() is Resource.Success) {
                    val mergedResult = mutableListOf<Rate>()
                    val officialList = resultOff.await().data!!
                    val commercialList = resultComm.await().data!!.commercialRatesList

                    mergedResult.addAll((commercialList.sortedBy { it.currency } zip
                            officialList.filter { official ->
                                commercialList.map { it.currency }
                                    .contains(official.currency)
                            }.sortedBy { it.currency }).map {
                        Rate(
                            off = it.second,
                            comm = it.first
                        )
                    })
                    _currencyStateFlow.value = ApiState.Success(mergedResult)
                } else {
                    ApiState.Failure(resultComm.await().message.toString())
                }
            } catch (e: Throwable) {
                ApiState.Failure(e.message.toString())
            }
        }

    }

}
