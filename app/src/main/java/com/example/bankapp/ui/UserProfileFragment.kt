package com.example.bankapp.ui


import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentUserProfileBinding
import com.example.bankapp.viewModels.CalculationSharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint

class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    private lateinit var auth:FirebaseAuth
    private val addViewModel: CalculationSharedViewModel by viewModels()
    val db = Firebase.firestore

    override fun start() {
        auth = FirebaseAuth.getInstance()
        //show currencies on user page
        setAmountUI()
        //transfer money
        transferMoneyToYourAccount()



//        navigate to conversion fragment
        binding.converterButton.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToConversionFragment())
        }
      //navigate to currencies or cryptocurrencies fragment
        binding.bottomNavView.setOnItemSelectedListener{ item->
            when(item.itemId) {
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
        //log out
        binding.appBar.setOnMenuItemClickListener {
            it->
            when(it.itemId){
                R.id.settings-> {
                    auth.signOut()
                    findNavController().navigate(
                        UserProfileFragmentDirections.actionUserProfileFragmentToAuthFragment(""))
                    true
                }else-> false

        }
        }


    }

    private fun transferMoneyToYourAccount(){
        binding.enroll.setOnClickListener {
            val amount = binding.addMoney.text.toString().toDouble()
            addViewModel.enroll(amount)
            updateAmountUI()
        }
    }
    private fun setAmountUI(){
        db.collection(auth.currentUser?.uid.toString()).document("Total").get().addOnSuccessListener {
            document->
            binding.amount.text = document["total"].toString()
        }
    }
    private fun updateAmountUI() {
        lifecycleScope.launchWhenCreated {
            addViewModel.total.collect {
                binding.amount.text = it.toString()
            }
        }
    }
}