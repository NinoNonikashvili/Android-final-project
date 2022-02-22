package com.example.bankapp.ui

import android.util.Log
import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankapp.databinding.FragmentAuthBinding
import com.example.bankapp.viewModels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
private lateinit var auth: FirebaseAuth
private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        auth = FirebaseAuth.getInstance()
        sendDataToViewModel()
        setErrorIfIncorrectInput()
        resetPassword()
        if(auth.currentUser!=null){
            binding.login.isEnabled = true
            val args: AuthFragmentArgs by navArgs()
            binding.email.setText(auth.currentUser?.email)
            binding.password.setText(args.password)
        }
        else{
            setLoginBtnState()

        }

        binding.login.setOnClickListener{
            signIn(binding.email.text.toString(),binding.password.text.toString())

        }

        binding.register.setOnClickListener {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToRegFirstFragment())
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
                binding.emailWrapper.error = "შეიყვანეთ სწორი ელ.ფოსტა"
        }
        binding.password.setOnFocusChangeListener { v, hasFocus ->
            binding.passwordWrapper.error = null
            if (!hasFocus&&!loginViewModel.isPasswordValid)
                binding.passwordWrapper.error = "შეიყვანეთ სწორი პაროლი"
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
            if(task.isSuccessful) {
                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToUserProfileFragment())

            }
            else {
                binding.emailWrapper.error = "ელ.ფოსტა ან პაროლი არასწორია"
                binding.passwordWrapper.error = "ელ.ფოსტა ან პაროლი არასწორია"
            }
        }
    }

    private fun resetPassword(){
        binding.forgotPassword.setOnClickListener {
            val email = binding.email.text.toString()
            if(email.isNullOrEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                Snackbar.make(binding.forgotPassword, "შეიყვანეთ სწორი ელ.ფოსტა", Snackbar.LENGTH_LONG).show()
            else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(binding.forgotPassword, "ლინკი გამოგზავნილია მითითებულ ელ.ფოსტაზე", Snackbar.LENGTH_LONG).show()

                    } else {
                        Snackbar.make(binding.forgotPassword, "ლინკის გამოგზავნა ვერ მოხერხდა", Snackbar.LENGTH_LONG).show()

                    }
                }

            }
        }
    }
}
