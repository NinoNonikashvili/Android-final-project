package com.example.bankapp.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.visible() {
    this.isVisible = true
}

fun View.invisible() {
    this.isVisible = false
}