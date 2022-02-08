package com.example.bankapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SwipeAdapter(fragment: Fragment, private val items:List<Fragment>):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment = items[position]
}