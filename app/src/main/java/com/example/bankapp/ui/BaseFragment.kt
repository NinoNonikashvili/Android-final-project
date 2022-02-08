package com.example.bankapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.bankapp.R
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<T : ViewBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {
    private var _binding: T? = null
    protected val binding: T
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

    abstract fun start()

}