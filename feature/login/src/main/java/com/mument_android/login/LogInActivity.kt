package com.mument_android.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.login.databinding.ActivityLogInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LogInActivity : BaseActivity<ActivityLogInBinding>(ActivityLogInBinding::inflate) {

    private val viewModel : LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webLinkNetwork()
    }

    private fun webLinkNetwork() {

        viewModel.getWebView("login")
        viewModel.getWebView.observe(this) {
            val tosLink = it.tos.toString()
            val privacyLink = it.privacy.toString()
            binding.tvTermsOfService.setOnClickListener {
                initIntent(tosLink)
            }
            binding.tvPrivacyPolicy.setOnClickListener {
                initIntent(privacyLink)
            }
        }

    }

    fun initIntent(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        ContextCompat.startActivity(this, intent, null)
    }
}