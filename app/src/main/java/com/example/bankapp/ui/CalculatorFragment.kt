package com.example.bankapp.ui


import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bankapp.R
import com.example.bankapp.currency.ConvertViewModel
import com.example.bankapp.databinding.FragmentCalculatorBinding
import com.example.bankapp.extensions.invisible
import com.example.bankapp.extensions.roundDecimal
import com.example.bankapp.extensions.visible
import com.example.bankapp.model.ConvertInfo
import com.example.bankapp.util.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalculatorFragment : BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val convertViewModel: ConvertViewModel by viewModels()

    override fun start() {
        setAdapter()
        binding.IVConvert.setOnClickListener {
            convertData()
        }
        observe()

   }

    private fun setAdapter(){
        val currencyList = ArrayAdapter(requireContext(), R.layout.list_currencies, resources.getStringArray(R.array.currency_codes))
        binding.ATVCurrenciesDropDown1.apply {
            setText("USD")
            setAdapter(currencyList)
        }
        binding.ATVCurrenciesDropDown2.apply {
            setText("GEL")
            setAdapter(currencyList)
        }
    }

    private fun convertData() {
        val fromCurrency = binding.ATVCurrenciesDropDown1.text.toString()
        val toCurrency = binding.ATVCurrenciesDropDown2.text.toString()
        val amount = binding.ETAmountToConvert.text.toString()
        convertViewModel.convert(amount, fromCurrency, toCurrency)

    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                convertViewModel.convertStateFlow.collect {
                    when (it) {
                        is ApiState.Loading -> {
                            binding.progressBar.visible()
                        }
                        is ApiState.Failure -> {
                            Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                        }
                        is ApiState.Success<*> -> {
                            binding.progressBar.invisible()
                            val result = it.data as ConvertInfo
                            binding.TVConvertedMoney.setText( result.value.roundDecimal(1))

                        }
                        is ApiState.Empty -> {
                            Log.d("mm1", "empty")

                        }
                    }

                }
            }
        }

    }




}