package com.example.bankapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.bankapp.R
import com.example.bankapp.databinding.FragmentUserProfileBinding


class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    override fun start() {

        val currencies = resources.getStringArray(R.array.currencies)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, currencies)
        binding.currency.setAdapter(arrayAdapter)


    }
}