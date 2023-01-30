package com.mument_android.login

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.login.databinding.ActivityLogInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : BaseActivity<ActivityLogInBinding>(R.layout.activity_log_in) {
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