package com.example.bankapp.ui


import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentConversionBinding
import com.example.bankapp.extensions.invisible
import com.example.bankapp.extensions.roundDecimal
import com.example.bankapp.extensions.visible
import com.example.bankapp.model.ConvertInfo
import com.example.bankapp.util.ApiState
import com.example.bankapp.viewModels.CalculationSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConversionFragment : BaseFragment<FragmentConversionBinding>(FragmentConversionBinding::inflate) {

    private val convertViewModel: CalculationSharedViewModel by viewModels()

    override fun start() {
        setAdapter()
        binding.convertButton.setOnClickListener {
            convertData()
        }
        observe()

    }

    private fun setAdapter(){
        val currencyList = ArrayAdapter(requireContext(), R.layout.list_currencies, resources.getStringArray(R.array.currency_codes))
        binding.ATVCurrenciesDropDown.apply {
            setText("USD")
            setAdapter(currencyList)
        }

    }

    private fun convertData() {
        val fromCurrency = "GEL"
        val toCurrency = binding.ATVCurrenciesDropDown.text.toString()
        val amount = binding.forSaleMoney.text.toString()
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
                            binding.convertedMoney.text = result.value.roundDecimal(2)
                        }
                        is ApiState.Empty -> {

                        }
                    }

                }
            }
        }

    }

}