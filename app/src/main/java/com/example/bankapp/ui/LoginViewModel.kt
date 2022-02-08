package com.example.bankapp.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class LoginViewModel: ViewModel() {

    private val email = MutableStateFlow("")
    private val password = MutableStateFlow("")
    var isEmailValid = false
    var isPasswordValid = false

    fun setEmail(emailInput:String){
        email.value = emailInput
    }
    fun setPassword(passwordInput:String){
        password.value = passwordInput
    }

    val isButtonEnabled: Flow<Boolean> = combine(
        email, password
    ){  email, password ->
        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordValid= password.length in 9..15
        return@combine isEmailValid and isPasswordValid
    }
}