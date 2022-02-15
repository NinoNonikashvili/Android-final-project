package com.example.bankapp.ui


import android.util.Log
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
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
    private val addViewModel: CalculationSharedViewModel by viewModels()
    val db = Firebase.firestore

    override fun start() {
        auth = FirebaseAuth.getInstance()
        //show currencies on user page
        setAmountUI()
        //transfer money
        transferMoneyToYourAccount()
        addTabOnUserPage()
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
        binding.enroll.setOnClickListener {
            val amount = binding.addMoney.text.toString()

            if (amount.isNotEmpty()) {
                addViewModel.addMoney(amount.toDouble())
                updateAmountUI()
            }

        }
    }

    private fun setAmountUI() {
        db.collection(auth.currentUser?.uid.toString()).document("Total").get()
            .addOnSuccessListener { document ->
                document["total"]?.let {
                    binding.amount.text = it.toString()
                }
            }
    }

    private fun updateAmountUI() {
        lifecycleScope.launchWhenCreated {
            addViewModel.total.collect {
                binding.amount.text = it.toString()
            }
        }
    }

    private fun addTabOnUserPage() {
        val tabList = mutableMapOf<String, Int>()
        var count = 0
        addViewModel.updateTabs()
        lifecycleScope.launch {
            addViewModel.tab.collect {
                it.forEach { (currencyName, currencyAmount) ->
                    if(tabList.keys.contains(currencyName)){
                        binding.currenciesTabLayout.getTabAt(tabList[currencyName]!!)
                            ?.text = "${currencyAmount.toString().toDouble().roundDecimal(2)} $currencyName"
                    }else{
                        tabList[currencyName] = count
                        binding.currenciesTabLayout.addTab(binding.currenciesTabLayout.newTab()
                            .setText("${currencyAmount.toString().toDouble().roundDecimal(2)} $currencyName"), count)
                        count++
                    }
                }

            }
        }
    }
}