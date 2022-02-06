package com.example.bankapp.ui


import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bankapp.RegFirstViewModel
import com.example.bankapp.UserData
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
        val action = RegFirstFragmentDirections.actionRegFirstFragmentToRegSecondFragment(userData)
        binding.next.setOnClickListener {
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
            Log.d("TAG2", "entered id focus")

        }
        binding.phoneNum.setOnFocusChangeListener { v, hasFocus ->
            binding.mobileNumberWrapper.error = null
            if (!hasFocus&&!regViewModel.isUserIdValid)
                binding.mobileNumberWrapper.error = "Enter using format: 5XXXXXXXX"
            Log.d("TAG2", "entered num focus")

        }
        binding.first6numbers.setOnFocusChangeListener { v, hasFocus ->
            binding.firstNumbersWrapper.error = null
            if (!hasFocus&&!regViewModel.isUserIdValid)
                binding.firstNumbersWrapper.error = "Enter valid card digits"

        }
        binding.last4numbers.setOnFocusChangeListener { v, hasFocus ->
            binding.lastNumbersWrapper.error = null
            if (!hasFocus&&!regViewModel.isUserIdValid)
                binding.lastNumbersWrapper.error = "Enter valid card digits"

        }
        binding.expireDate.setOnFocusChangeListener { v, hasFocus ->
            binding.expireDateWrapper.error = null
            if (!hasFocus&&!regViewModel.isUserIdValid)
                binding.expireDateWrapper.error = "Enter valid date XX/XX"

        }
        binding.cvcCode.setOnFocusChangeListener { v, hasFocus ->
            binding.cvcCodeWrapper.error = null
            if (!hasFocus&&!regViewModel.isUserIdValid)
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
                userData = UserData(
                    binding.userId.toString(),
                    binding.phoneNum.toString(),
                    binding.first6numbers.toString()+binding.last4numbers.toString(),
                    binding.expireDate.toString(),
                    binding.cvcCode.toString()
                )

            }
        }
    }
}