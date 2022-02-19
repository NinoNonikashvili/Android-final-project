package com.example.bankapp.ui


import android.util.Log
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.currency.CalculateViewModel
import com.example.bankapp.databinding.FragmentUserProfileBinding
import com.example.bankapp.extensions.roundDecimal
import com.example.bankapp.viewModels.CalculationSharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint

class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    private val viewModel: CalculationSharedViewModel by viewModels()


    override fun start() {
        auth = FirebaseAuth.getInstance()
        //request to fireStore
        viewModel.retrieveData()
        //set UI according to the received data
        setUI()

        //transfer money
        transferMoneyToYourAccount()
//        addTabOnUserPage()
        navClicks()
        logout()


    }

    private fun navClicks() {
        //navigate to conversion fragment

        binding.converterButton.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToConversionFragment())
        }
        //navigate to currencies or cryptocurrencies fragment

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.currencyRates -> {
                    findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToSwipeFragment())

                    true
                }
                R.id.cryptoCurrencyRates -> {
                    findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToCryptoCurrenciesFragment())
                    true
                }
                else ->
                    false
            }
        }
    }

    private fun logout() {
        binding.appBar.setOnMenuItemClickListener { it ->
            when (it.itemId) {
                R.id.settings -> {
                    auth.signOut()
                    findNavController().navigate(
                        UserProfileFragmentDirections.actionUserProfileFragmentToAuthFragment("")
                    )
                    true
                }
                else -> false

            }
        }
    }

    private fun transferMoneyToYourAccount() {
        binding.addMoneyBtn.setOnClickListener {
            val amount = binding.addMoney.text.toString()

            if (amount.isNotEmpty()) {
                viewModel.addMoney(amount.toDouble())
            }

        }
    }


    private fun setUI(){
        Log.d("TAG2", "set ui")

        lifecycleScope.launch {   //repeatOnLifecycle
            viewModel.totalAmount.collect {
                binding.amount.text = it.roundDecimal(2)
                Log.d("TAG2", "collected Total")

            }
        }
        lifecycleScope.launch {
            viewModel.listFlag.collect {
                Log.d("TAG2", "collected currencyList ${viewModel._currencyList}")
                //send map to updateTabs function
                addTabOnUserPage(viewModel._currencyList)
            }
        }


    }



    private fun addTabOnUserPage(currencyMap:MutableMap<String, Any>) {

        Log.d("TAG2", "show tabs list  $currencyMap")

        Log.d("TAG2", "started to update tabs")
                binding.currenciesTabLayout.removeAllTabs()
        currencyMap.forEach { (currencyName, currencyAmount) ->
            binding.currenciesTabLayout.addTab(binding.currenciesTabLayout.newTab()
                .setText("${currencyAmount.toString().toDouble().roundDecimal(2)} $currencyName"))
        }

    }
}