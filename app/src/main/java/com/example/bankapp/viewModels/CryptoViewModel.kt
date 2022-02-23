package com.example.bankapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.repositories.CryptoRepository
import com.example.bankapp.model.CryptoData
import com.example.bankapp.util.ApiState
import com.example.bankapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository: CryptoRepository
):ViewModel(){



    private val _state = MutableStateFlow<ApiState>(ApiState.Empty)
    val state : StateFlow<ApiState> = _state
    var deliveredCryptoList = CryptoData()



    fun getData(){
        viewModelScope.launch(Dispatchers.IO){
            _state.value = ApiState.Loading
            when(val response = repository.getData()){
                is Resource.Success->{
                    _state.value = ApiState.Success("success")
                    deliveredCryptoList = response.data!!
                    Log.d("TAG3", "${deliveredCryptoList.size}")

                }
                is Resource.Error->{
                    _state.value = ApiState.Failure("error")
                }
            }
        }
    }



}