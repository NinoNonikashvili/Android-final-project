package com.example.bankapp.ui


import android.widget.ArrayAdapter
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentCalculatorBinding


class CalculatorFragment : BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {
    override fun start() {
        val currencies = listOf("Opt 1", "Opt 2", "Opt 3", "Opt 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_currencies, currencies)
        binding.ATVCurrenciesDropDown1.setAdapter(adapter)
        binding.ATVCurrenciesDropDown1.setText("USD", false)
        binding.ATVCurrenciesDropDown2.setAdapter(adapter)
        binding.ATVCurrenciesDropDown2.setText("USD", false)
   }

}