package com.mument_android.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.login.databinding.FragmentOnBoardingSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingSecondFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentOnBoardingSecondBinding>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOnBoardingSecondBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }
}