package com.example.bankapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData (
    val userId: String,
    val phoneNum: String,
    val cardNumber:String,
    val expirationDate:String,
    val cvc:String
        ):Parcelable