package com.mument_android.locker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mument_android.locker.MyLikeFragment
import com.mument_android.locker.MyMumentFragment

class LockerTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentList = mutableListOf<Fragment>(MyMumentFragment(), MyLikeFragment())

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

}