package com.mument_android.login

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.login.databinding.ActivityLogInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LogInActivity : BaseActivity<ActivityLogInBinding>(ActivityLogInBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener()
    }

    private fun clickListener() {
        binding.tvTermsOfService.setOnClickListener {
            initIntent("https://www.naver.com/")
        }
        binding.tvPrivacyPolicy.setOnClickListener {
            initIntent("https://www.naver.com/")
        }
    }

    fun initIntent(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        ContextCompat.startActivity(this, intent, null)
    }
}