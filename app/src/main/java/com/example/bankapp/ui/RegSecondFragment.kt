package com.example.bankapp.ui


import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankapp.RegSecondVIewModel
import com.example.bankapp.model.UserData
import com.example.bankapp.databinding.FragmentRegSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RegSecondFragment : BaseFragment<FragmentRegSecondBinding>(FragmentRegSecondBinding::inflate) {
    private val args: RegSecondFragmentArgs by navArgs()
    private val reg2ViewModel:RegSecondVIewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun start() {
        auth = FirebaseAuth.getInstance()
        sendDataToViewModel()
        setErrorIfIncorrectInput()
        setButtonState()


        binding.register.setOnClickListener {
            val userInfo = args.userDetails
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString()
            registerUser(email, password, userInfo)

        }
        binding.goBack.setOnClickListener {
            val password = binding.password.text.toString()

            val action = RegSecondFragmentDirections.actionRegSecondFragmentToAuthFragment(password)
            findNavController().navigate(action)
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
    private fun setButtonState(){
        lifecycleScope.launch {
            reg2ViewModel.isInputValid.collect {
                binding.register.isEnabled = it
            }
        }
    }
    private fun registerUser(email:String, password:String, userInfo: UserData){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() {task->
            //progressbar
            if(task.isSuccessful){
                Log.d("TAG1", "createUserWithEmail:success")
                saveDataInFireStore(userInfo)

            }
            else{
                Log.d("TAG1", "createUserWithEmail:FAILURE ", task.exception)

            }
        }

    }
    private fun saveDataInFireStore(userInfo: UserData){
        val userId = auth.currentUser?.uid.toString()
        db.collection(userId).add(userInfo)
            .addOnSuccessListener { Log.d("TAG1", "added successfully") }
            .addOnFailureListener { e-> Log.d("TAG1", "could not add", e) }
    }
}