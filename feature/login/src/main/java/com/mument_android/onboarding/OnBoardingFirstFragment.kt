package com.mument_android.onboarding


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.login.databinding.FragmentOnBoardingFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFirstFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentOnBoardingFirstBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOnBoardingFirstBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}

