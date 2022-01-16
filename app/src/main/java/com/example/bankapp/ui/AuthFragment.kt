package com.example.bankapp.ui

import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentAuthBinding


class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    override fun start() {
        binding.login.setOnClickListener{
            findNavController().navigate(R.id.action_authFragment_to_swipeFragment)

        }

    }

}
