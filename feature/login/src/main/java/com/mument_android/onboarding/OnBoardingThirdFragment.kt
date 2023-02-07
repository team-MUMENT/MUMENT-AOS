package com.mument_android.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.login.LogInActivity
import com.mument_android.login.LogInViewModel
import com.mument_android.login.databinding.FragmentOnBoardingThirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingThirdFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentOnBoardingThirdBinding>()
    private val viewModel : LogInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOnBoardingThirdBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startBtnClickListener()
    }

    private fun startBtnClickListener() {
        binding.btnFinish.setOnClickListener {
            val intent = Intent(activity, LogInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


}