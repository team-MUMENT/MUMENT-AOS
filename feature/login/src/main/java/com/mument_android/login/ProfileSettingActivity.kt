package com.mument_android.login

import android.os.Bundle
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.login.databinding.ActivityProfileSettingBinding

class ProfileSettingActivity :  BaseActivity<ActivityProfileSettingBinding>(R.layout.activity_profile_setting) {

    private val viewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }



}