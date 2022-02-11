package com.example.bankapp.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankapp.adapters.CurrencyAdapter
import com.example.bankapp.currency.CurrencyViewModel
import com.example.bankapp.databinding.FragmentCurrenciesBinding
import com.example.bankapp.extensions.invisible
import com.example.bankapp.extensions.visible
import com.example.bankapp.model.Rate
import com.example.bankapp.util.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrenciesFragment :
    BaseFragment<FragmentCurrenciesBinding>(FragmentCurrenciesBinding::inflate) {
    private lateinit var currencyAdapter: CurrencyAdapter
    private val currencyViewModel: CurrencyViewModel by viewModels()


    override fun start() {
        initRecycler()
        currencyViewModel.getCurrency()
        observe()

    }

    private fun initRecycler() {
        currencyAdapter = CurrencyAdapter()
        binding.currencyRecycler.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                currencyViewModel.currencyStateFlow.collect {
                    when (it) {
                        is ApiState.Loading -> {
                            binding.currencyRecycler.invisible()
                            binding.progressBar.visible()
                        }
                        is ApiState.Failure -> {
                            binding.currencyRecycler.invisible()
                            binding.progressBar.invisible()
                        }
                        is ApiState.Success<*> -> {
                            binding.currencyRecycler.visible()
                            binding.progressBar.invisible()
                            currencyAdapter.differ.submitList(it.data as List<Rate>)
                        }
                        is ApiState.Empty -> {

                        }
                    }

                }
            }
        }

    }


}