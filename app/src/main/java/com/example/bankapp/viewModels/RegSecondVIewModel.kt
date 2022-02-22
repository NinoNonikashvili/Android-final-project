package com.example.bankapp.viewModels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class RegSecondVIewModel:ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _repeatedPassword = MutableStateFlow("")
    var isEmailValid = false
    var isPasswordValid = false
    var isRepeatedPasswordValid = false

    fun setEmail(inputEmail:String){
        _email.value = inputEmail
    }
    fun setPassword(inputPassword:String){
        _password.value = inputPassword
    }
    fun setRepeatedPassword(inputRepeatedPassword:String){
        _repeatedPassword.value = inputRepeatedPassword
    }

    val isInputValid: Flow<Boolean> = combine(
        _email,
        _password,
        _repeatedPassword
    ){ email, password, repeatedPassword ->
        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordValid = password.length in 9..15
        isRepeatedPasswordValid = repeatedPassword==password
       return@combine isEmailValid and isPasswordValid and isRepeatedPasswordValid
    }
}