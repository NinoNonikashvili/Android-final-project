package com.example.bankapp.ui


import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankapp.Crypto.CryptoViewModel
import com.example.bankapp.adapters.CryptoCurrenciesAdapter
import com.example.bankapp.databinding.FragmentCryptoCurrenciesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CryptoCurrenciesFragment :
    BaseFragment<FragmentCryptoCurrenciesBinding>(FragmentCryptoCurrenciesBinding::inflate) {
    private lateinit var cryptoAdapter: CryptoCurrenciesAdapter
    private val viewModel: CryptoViewModel by viewModels()

    override fun start() {

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val cryptoRecycler = binding.RVCryptoRecyclerView
        cryptoRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cryptoAdapter = CryptoCurrenciesAdapter()
        cryptoRecycler.adapter = cryptoAdapter
        viewModel.getData()
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect { event ->
                when (event) {
                    is CryptoViewModel.States.Success -> {
                        Log.d("nio", "${viewModel.deliveredCryptoList[0].id}")
                        cryptoAdapter.cryptoData = viewModel.deliveredCryptoList
                    }
                    is CryptoViewModel.States.Failure -> {
                        Log.d("nio", "failure")
                    }
                    is CryptoViewModel.States.Empty -> {
                        Log.d("nio", "empty")
                    }
                    is CryptoViewModel.States.Loading -> {
                        Log.d("nio", "loading")
                    }

                }
            }
        }
    }


}