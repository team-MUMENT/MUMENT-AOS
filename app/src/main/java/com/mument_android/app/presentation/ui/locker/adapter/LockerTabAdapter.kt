package com.mument_android.app.presentation.ui.locker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mument_android.app.presentation.ui.locker.MyMumentFragment

class LockerTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    val fragment = mutableListOf<Fragment>()

    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]

}