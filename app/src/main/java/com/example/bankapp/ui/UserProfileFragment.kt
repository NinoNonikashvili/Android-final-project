package com.example.bankapp.ui


import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth


class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    private lateinit var auth:FirebaseAuth
    override fun start() {
        auth = FirebaseAuth.getInstance()
        val currencies = resources.getStringArray(R.array.currencies)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, currencies)
        binding.currency.setAdapter(arrayAdapter)
        binding.converterButton.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToSwipeFragment())
        }
        binding.cardNumber.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToCryptoCurrenciesFragment())
        }
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
}