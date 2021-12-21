package com.example.bankapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.bankapp.R


abstract class BaseFragment<T: ViewBinding>(private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment() {
    var _binding:T? = null
    val binding:T
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        start()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    abstract  fun start()

}