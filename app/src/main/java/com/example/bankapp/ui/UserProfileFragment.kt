package com.example.bankapp.ui


import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentUserProfileBinding


class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    override fun start() {

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
                    findNavController().navigate(
                        UserProfileFragmentDirections.actionUserProfileFragmentToAuthFragment(""))
                    true
                }else-> false

        }
        }


    }
}