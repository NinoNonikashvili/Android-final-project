package com.example.bankapp.ui

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentAuthBinding
import com.google.firebase.auth.FirebaseAuth


class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
private lateinit var auth: FirebaseAuth

    override fun start() {

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_regFirstFragment)
        }
    }

}
