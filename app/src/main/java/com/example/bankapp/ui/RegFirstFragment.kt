package com.example.bankapp.ui


import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bankapp.viewModels.RegFirstViewModel
import com.example.bankapp.model.UserData
import com.example.bankapp.databinding.FragmentRegFirstBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegFirstFragment : BaseFragment<FragmentRegFirstBinding>(FragmentRegFirstBinding::inflate) {

    private val regViewModel: RegFirstViewModel by viewModels()
    private lateinit var userData: UserData
    override fun start() {

        sendDataToViewModel()
        addErrorIfIncorrectInput()
        setButtonState()
        binding.next.setOnClickListener {
            userData = UserData(
                binding.userId.text.toString(),
                binding.phoneNum.text.toString(),
                binding.first6numbers.text.toString()+binding.last4numbers.text.toString(),
                binding.expireDate.text.toString(),
                binding.cvcCode.text.toString()
            )
            Log.d("TAG1", "$userData")
            val action = RegFirstFragmentDirections.actionRegFirstFragmentToRegSecondFragment(userData)

            findNavController().navigate(action)
        }
    }

    private fun sendDataToViewModel(){
        binding.userId.addTextChangedListener {
            regViewModel.setUserId(it.toString())

        }
        binding.phoneNum.addTextChangedListener {
            regViewModel.setPhoneNum(it.toString())
        }
        binding.first6numbers.addTextChangedListener {
            regViewModel.setFirstDigits(it.toString())
        }
        binding.last4numbers.addTextChangedListener {
            regViewModel.setLastDigits(it.toString())
        }
        binding.expireDate.addTextChangedListener {
            regViewModel.setExpDate(it.toString())
        }
        binding.cvcCode.addTextChangedListener {
            regViewModel.setCvc(it.toString())
        }
    }

    private fun addErrorIfIncorrectInput(){
        binding.userId.setOnFocusChangeListener { v, hasFocus ->
            binding.userIdWrapper.error = null
            if (!hasFocus&&!regViewModel.isUserIdValid)
                binding.userIdWrapper.error = "Enter valid ID"

        }
        binding.phoneNum.setOnFocusChangeListener { v, hasFocus ->
            binding.mobileNumberWrapper.error = null
            if (!hasFocus&&!regViewModel.isPhoneNumValid){
                binding.mobileNumberWrapper.error = "Enter using format: 5XXXXXXXX"
            }

        }
        binding.first6numbers.setOnFocusChangeListener { v, hasFocus ->
            binding.firstNumbersWrapper.error = null
            if (!hasFocus&&!regViewModel.isFirstNumValid)
                binding.firstNumbersWrapper.error = "Enter valid card digits"

        }
        binding.last4numbers.setOnFocusChangeListener { v, hasFocus ->
            binding.lastNumbersWrapper.error = null
            if (!hasFocus&&!regViewModel.isLastNumValid)
                binding.lastNumbersWrapper.error = "Enter valid card digits"

        }
        binding.expireDate.setOnFocusChangeListener { v, hasFocus ->
            binding.expireDateWrapper.error = null
            if (!hasFocus&&!regViewModel.isExpDateValid)
                binding.expireDateWrapper.error = "Enter valid date XX/XX"

        }
        binding.cvcCode.setOnFocusChangeListener { v, hasFocus ->
            binding.cvcCodeWrapper.error = null
            if (!hasFocus&&!regViewModel.isCvcValid)
                binding.cvcCodeWrapper.error = "Enter valid cvc"

        }

    }

    private fun setButtonState(){
        lifecycleScope.launch {
            regViewModel.isInputValid.collect {
                binding.next.isEnabled = it
                Log.d("Tag2", "btn $it")
                Log.d("Tag2", "userId ${regViewModel.isUserIdValid}")
                Log.d("Tag2", "pnone ${regViewModel.isPhoneNumValid}")
                Log.d("Tag2", "first ${regViewModel.isFirstNumValid}")
                Log.d("Tag2", "last ${regViewModel.isLastNumValid}")
                Log.d("Tag2", "date ${regViewModel.isExpDateValid}")
                Log.d("Tag2", "cvc ${regViewModel.isCvcValid}")
                //wrap inputs in user data class


            }
        }
    }
}