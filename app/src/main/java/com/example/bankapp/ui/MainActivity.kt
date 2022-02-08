package com.example.bankapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bankapp.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BankApp)
        setContentView(R.layout.activity_main)


    }
}