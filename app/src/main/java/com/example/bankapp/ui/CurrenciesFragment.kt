package com.example.bankapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentCurrenciesBinding


class CurrenciesFragment : BaseFragment<FragmentCurrenciesBinding> (FragmentCurrenciesBinding::inflate){
    override fun start() {
        binding.TVChooseCalculator.setOnClickListener {
            findNavController().navigate(R.id.action_currenciesFragment_to_calculatorFragment)
        }

    }
}