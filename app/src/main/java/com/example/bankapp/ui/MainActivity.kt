package com.example.bankapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bankapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BankApp)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null)
            auth.signOut()
    }

    override fun onResume() {
        Log.d("SIGNOUT", "USER in onresume")

        super.onResume()
    }




}