package com.mument_android.locker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    val fragment = mutableListOf<Fragment>()

    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]

}