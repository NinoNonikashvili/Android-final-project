package com.example.bankapp.Crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.model.CryptoData
import com.example.bankapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository:CryptoRepository
):ViewModel(){

    sealed class States {
        class Success(val resultText: String) : States()
        class Failure(val errorText: String) : States()
        object Loading : States()
        object Empty : States()
    }

    private val _state = MutableStateFlow<States>(States.Empty)
    val state = _state
    lateinit var deliveredCryptoList : CryptoData



    fun getData(){
        viewModelScope.launch(Dispatchers.IO){
            _state.value = States.Loading
            when(val response = repository.getData()){
                is Resource.Success->{
                    _state.value = States.Success("success")
                    deliveredCryptoList = response.data!!
                }
                is Resource.Error->{
                    _state.value = States.Failure("error")
                }
            }
        }
    }



}