package com.mument_android.login

import android.os.Bundle
import android.system.Os.remove
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.login.databinding.ActivityProfileSettingBinding
import java.util.regex.Pattern

class ProfileSettingActivity :  BaseActivity<ActivityProfileSettingBinding>(R.layout.activity_profile_setting) {

    private val viewModel: LogInViewModel by viewModels()
    private val finalNickname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        isRightPattern()
        isSpace()
    }

    private fun isRightPattern() {
        val nickname = viewModel.mumentNickName
        if(!Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,15}\$", nickname.toString())) {
            viewModel.isRightPattern.value = false
            binding.tvPattern.isSelected = true
        } else {
            viewModel.isRightPattern.value = true
            binding.tvPattern.isSelected = false
        }
    }

    private fun isSpace() {
        binding.etNickname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val str = p0?.trim().toString()
                viewModel.isActive.value = str.length >= 2
            }

        })
    }



}