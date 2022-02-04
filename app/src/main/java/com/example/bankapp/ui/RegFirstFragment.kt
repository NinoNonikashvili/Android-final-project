package com.example.bankapp.ui


import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.RegViewModel
import com.example.bankapp.UserInfo
import com.example.bankapp.databinding.FragmentRegFirstBinding

class RegFirstFragment : BaseFragment<FragmentRegFirstBinding>(FragmentRegFirstBinding::inflate) {
    private val viewModel: RegViewModel by viewModels()
    override fun start() {
        val userId = binding.userId.toString()
        val phoneNum = binding.phoneNum.toString()
        val firstSixNum = binding.first6numbers.toString()
        val lastFourNum = binding.last4numbers.toString()
        val expirationDate = binding.expireDate.toString()
        val cvc = binding.cvcCode.toString()
        val userInfo = UserInfo(userId, phoneNum, firstSixNum, lastFourNum, expirationDate, cvc)
        val action = RegFirstFragmentDirections.actionRegFirstFragmentToRegSecondFragment(userInfo)


        binding.next.setOnClickListener{

        }




    }


}