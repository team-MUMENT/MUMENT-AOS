package com.mument_android.onboarding

import OnBoardingSecondFragment
import OnBoardingThirdFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.login.databinding.ActivityOnBoardingBinding
import com.mument_android.onboarding.OnBoardingFirstFragment

class OnBoardingActivity :  BaseActivity<ActivityOnBoardingBinding>(ActivityOnBoardingBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val pagerAdater = OnBoardingPagerAdapter(this)
        binding.vpOnboarding.adapter = pagerAdater
        binding.dotsIndicator.setViewPager2(binding.vpOnboarding)

    }

    private inner class OnBoardingPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {

            return when (position) {
                0 -> OnBoardingFirstFragment()
                1 -> OnBoardingSecondFragment()
                else -> OnBoardingThirdFragment()
            }
        }
    }
}