package com.example.bankapp.ui

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.rpc.context.AttributeContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
private lateinit var auth: FirebaseAuth
private val loginViewModel:LoginViewModel by viewModels()

    override fun start() {
        auth = FirebaseAuth.getInstance()
        val email = auth.currentUser?.email
        Log.d("TAG5", "$email")

        if(auth.currentUser!=null){
            Log.d("TAG5", "entered here")
            val args: AuthFragmentArgs by navArgs()
            binding.email.setText(auth.currentUser?.email)
            binding.password.setText(args.password)
        }
        sendDataToViewModel()
        setErrorIfIncorrectInput()
        setLoginBtnState()
        binding.login.setOnClickListener{
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToUserFragment())
            signIn(binding.email.text.toString(),binding.password.text.toString())

        }

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_regFirstFragment)
        }
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_userProfileFragment)
        }
    }
    private fun sendDataToViewModel(){
        binding.email.addTextChangedListener {
                loginViewModel.setEmail(it.toString())
            }


            binding.password.addTextChangedListener {
                loginViewModel.setPassword(it.toString())
            }
        }
    private fun setErrorIfIncorrectInput(){
        binding.email.setOnFocusChangeListener { v, hasFocus ->
            binding.emailWrapper.error = null
            if (!hasFocus&&!loginViewModel.isEmailValid)
                binding.emailWrapper.error = "type correct email"
        }
        binding.password.setOnFocusChangeListener { v, hasFocus ->
            binding.passwordWrapper.error = null
            if (!hasFocus&&!loginViewModel.isPasswordValid)
                binding.passwordWrapper.error = "type correct email"
        }
    }

    private fun setLoginBtnState(){
        lifecycleScope.launch{
            loginViewModel.isButtonEnabled.collect {
                binding.login.isEnabled = it
            }
        }
    }
    private fun signIn(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
            task->
            if(task.isSuccessful)
                Log.d("TAG7", "log in success")
            else
                Log.d("TAG7", "Log in failure", task.exception)
        }
    }

}
