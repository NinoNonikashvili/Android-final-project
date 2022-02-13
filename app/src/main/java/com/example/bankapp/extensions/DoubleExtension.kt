package com.example.bankapp.extensions

fun Double.roundDecimal(digit: Int) = "%.${digit}f".format(this)
