package com.example.bankapp.ui


import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankapp.R
import com.example.bankapp.viewModels.CryptoViewModel
import com.example.bankapp.adapters.CryptoCurrenciesAdapter
import com.example.bankapp.databinding.FragmentCryptoCurrenciesBinding
import com.example.bankapp.extensions.invisible
import com.example.bankapp.extensions.visible
import com.example.bankapp.util.ApiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CryptoCurrenciesFragment : BaseFragment<FragmentCryptoCurrenciesBinding>(FragmentCryptoCurrenciesBinding::inflate) {
   private lateinit var cryptoAdapter : CryptoCurrenciesAdapter
   private val viewModel : CryptoViewModel by viewModels()

    override fun start() {

        initRecyclerView()
    }
    private fun initRecyclerView(){
       val cryptoRecycler =  binding.RVCryptoRecyclerView
        cryptoRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cryptoAdapter = CryptoCurrenciesAdapter()
        cryptoRecycler.adapter = cryptoAdapter
        viewModel.getData()
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect { event->
                when(event){
                    is ApiState.Success<*> ->{
                        cryptoAdapter.cryptoData = viewModel.deliveredCryptoList
                        binding.cryptoProgressBar.invisible()

                    }
                    is ApiState.Failure->{
                        binding.cryptoProgressBar.invisible()
                        Snackbar.make(binding.cryptoProgressBar, event.msg,Snackbar.LENGTH_LONG)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.blue))
                            .show()
                    }
                    is ApiState.Empty->{
                        binding.cryptoProgressBar.invisible()

                    }
                    is ApiState.Loading->{
                        binding.cryptoProgressBar.visible()

                    }
                   
                }
            }
        }
    }



}