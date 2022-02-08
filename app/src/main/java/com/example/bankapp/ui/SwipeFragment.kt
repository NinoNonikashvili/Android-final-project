package com.example.bankapp.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.bankapp.R
import com.example.bankapp.adapters.SwipeAdapter
import com.example.bankapp.databinding.FragmentSwipeBinding
import com.google.android.material.tabs.TabLayoutMediator


class SwipeFragment : BaseFragment<FragmentSwipeBinding>(FragmentSwipeBinding::inflate) {
    private lateinit var viewPager:ViewPager2
    private lateinit var swipeAdapter: SwipeAdapter
    override fun start() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragments = listOf<Fragment>(
            CurrenciesFragment(),
            CalculatorFragment()
        )
        viewPager = binding.pager
        swipeAdapter = SwipeAdapter(this, fragments)
        viewPager.adapter = swipeAdapter
        val tabLayout =binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = if(position%2==0) "${getString(R.string.currencies)}" else "${getString(R.string.calculator)}"
        }.attach()
    }

}