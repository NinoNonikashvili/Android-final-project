package com.example.bankapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentRegFirstBinding

class RegFirstFragment : BaseFragment<FragmentRegFirstBinding>(FragmentRegFirstBinding::inflate) {
    override fun start() {

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_regFirstFragment_to_regSecondFragment4)
        }
    }


}