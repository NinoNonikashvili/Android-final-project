package com.example.bankapp.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class RegFirstViewModel:ViewModel() {
    private val _userId = MutableStateFlow("")
    private val _phoneNumber = MutableStateFlow("")
    private val _first6digits = MutableStateFlow("")
    private val _last4digits = MutableStateFlow("")
    private val _expirationDate = MutableStateFlow("")
    private val _cvc = MutableStateFlow("")
    var isUserIdValid = false
    private val phoneRegex = """5\d{8}""".toRegex()
    private val expirationRegex = """[1-9]/[0-9]{2}|1[012]/[0-9]{2}""".toRegex()
    var isPhoneNumValid = false
    var isFirstNumValid = false
    var isLastNumValid = false
    var isExpDateValid = false
    var isCvcValid = false

    fun setUserId(userId: String){
        _userId.value = userId
    }
    fun setPhoneNum(phoneNumber: String){
        _phoneNumber.value = phoneNumber
    }
    fun setFirstDigits(firstDigits: String){
        _first6digits.value = firstDigits
    }
    fun setLastDigits(lastDigits: String){
        _last4digits.value = lastDigits
    }
    fun setExpDate(expDate: String){
        _expirationDate.value = expDate
    }
    fun setCvc(cvc: String){
        _cvc.value = cvc
    }


     val isInputValid: Flow<Boolean> = combine(
        _userId,
        _phoneNumber,
        _first6digits,
        _last4digits,
        _expirationDate,
        _cvc
    ){
         isUserIdValid = it[0].length==11
         isPhoneNumValid = phoneRegex.matches(it[1])
         isFirstNumValid = it[2].length == 6
         isLastNumValid = it[3].length == 4
         isExpDateValid = expirationRegex.matches(it[4])
         isCvcValid = it[5].length==3

      return@combine isUserIdValid and isPhoneNumValid and isCvcValid and isFirstNumValid and isLastNumValid and isExpDateValid
    }
}