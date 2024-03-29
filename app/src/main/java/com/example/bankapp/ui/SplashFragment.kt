package com.example.bankapp.ui


import androidx.navigation.fragment.findNavController
import com.example.bankapp.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate){
    private lateinit var auth: FirebaseAuth

    override  fun start() {
            auth = FirebaseAuth.getInstance()

            if (auth.currentUser!=null)
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToUserProfileFragment())
            else
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthFragment(""))



        }


    }



