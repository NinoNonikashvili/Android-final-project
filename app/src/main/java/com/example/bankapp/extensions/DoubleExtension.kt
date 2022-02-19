package com.example.bankapp.extensions


fun Double.roundDecimal(digit: Int) = "%.${digit}f".format(this)
fun Double.toBillionNotation() = (this/1_000_000_000).roundDecimal(3)+"B"