package com.example.bankapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.currency.CurrencyRepository
import com.example.bankapp.model.NewCurrencyTab
import com.example.bankapp.model.Total
import com.example.bankapp.util.ApiState
import com.example.bankapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CalculationSharedViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository) :
    ViewModel() {
    val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currencyList = mutableMapOf<String, Any>()
    private val userId = auth.currentUser?.uid.toString()
    private var totalAmount = Total(0.0)
    var total = MutableStateFlow(0.0)
    private val _convertStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val convertStateFlow: StateFlow<ApiState> = _convertStateFlow
    private val _tab = MutableStateFlow(mutableMapOf<String, Any>())
    val tab:StateFlow<MutableMap<String, Any>> = _tab


    fun convert(
        amount: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        //check if amount is empty string
        if(amount.isNullOrEmpty()){
            _convertStateFlow.value = ApiState.Failure("enter amount")
            return
        }

        //check if the user wants to convert more than he has
        var currentGelAmount = 0.0
        db.collection(auth.currentUser?.uid.toString()).document("CurrencyList")
            .get().addOnSuccessListener {
                if (it.data != null && it.data?.containsKey(fromCurrency) == true)

                    currentGelAmount = it.data?.get(fromCurrency).toString().toDouble()


        if(currentGelAmount < amount.toDouble())
            _convertStateFlow.value = ApiState.Failure("you do not have enough money")
        else {
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
                        updateCurrencyList(resultRates.data!!.value, toCurrency)
                        updateCurrencyList(-resultRates.data!!.amount, "GEL")
                        _tab.value = currencyList
                        Log.d("TAG2", "changed in convert function")



                    }
                }
            }
            }
        }
    }

    fun addMoney(amount:Double){
        db.collection(userId).document("Total").get().addOnSuccessListener { document ->

            totalAmount.Total = document.data?.get("total")?.toString()?.toDouble()?:0.0
            totalAmount.Total += amount
            total.value = totalAmount.Total
            updateTotal()
            updateCurrencyList(amount, "GEL")

        }

    }

    private fun updateTotal(){
        db.collection(userId).document("Total").set( totalAmount)
    }
    private fun updateCurrencyList( enrolledMoney:Double, toCurrency:String){
        var oldValue = 0.0
        db.collection(userId).document("CurrencyList")
            .get().addOnSuccessListener {
                if (it.data != null && it.data?.containsKey(toCurrency) == true) {
                    oldValue = it.data?.get(toCurrency).toString().toDouble()
                }

                val newValue = oldValue + enrolledMoney

                currencyList[toCurrency] = newValue

                db.collection(auth.currentUser?.uid.toString()).document("CurrencyList")
                    .set(currencyList, SetOptions.merge())
            }

    }
     fun updateTabs(){
         Log.d("TAG2", "entered here")

         db.collection(userId).document("CurrencyList").get().addOnSuccessListener {
            it.data?.let { it ->
                _tab.value = it
                Log.d("TAG2", "list changed $it")
            }
        }

    }

}





