package com.example.bankapp.ui


import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bankapp.RegSecondVIewModel
import com.example.bankapp.databinding.FragmentRegSecondBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RegSecondFragment : BaseFragment<FragmentRegSecondBinding>(FragmentRegSecondBinding::inflate) {

    private val reg2ViewModel:RegSecondVIewModel by viewModels()
    override fun start() {
        sendDataToViewModel()
        setErrorIfIncorrectInput()
        lifecycleScope.launch {
            reg2ViewModel.isInputValid.collect {
                binding.register.isEnabled = it
            }
        }
        binding.register.setOnClickListener {
            val email = binding.email.toString()
            val password = binding.password.toString()
            registerUser(email, password)
        }

    }

    private fun sendDataToViewModel(){
        binding.email.addTextChangedListener {
            reg2ViewModel.setEmail(it.toString())
        }
        binding.password.addTextChangedListener {
             reg2ViewModel.setPassword(it.toString())
        }
        binding.passwordRepeat.addTextChangedListener {
            reg2ViewModel.setRepeatedPassword(it.toString())
        }
    }

    private fun setErrorIfIncorrectInput(){
        binding.email.setOnFocusChangeListener { v, hasFocus ->
            binding.emailWrapper.error = null
            if(!hasFocus&&!reg2ViewModel.isEmailValid)
                binding.emailWrapper.error = "enter valid email"
        }
        binding.password.setOnFocusChangeListener { v, hasFocus ->
            binding.passwordWrapper.error = null
            if(!hasFocus&&!reg2ViewModel.isPasswordValid)
                binding.passwordWrapper.error = "min 9 and max 15 symbols"
        }
        binding.passwordRepeat.setOnFocusChangeListener { v, hasFocus ->
            binding.passwordRepeatWrapper.error = null
            if(!hasFocus&&!reg2ViewModel.isRepeatedPasswordValid)
                binding.passwordRepeatWrapper.error = "passwords do not match"
        }
    }



    private fun registerUser(email:String, password:String){

    }
}