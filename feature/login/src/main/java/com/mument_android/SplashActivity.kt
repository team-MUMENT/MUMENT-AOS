package com.mument_android.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.login.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplash()
        isFirst()
    }

    //스플래시 -> 우선은 로그인으로 가는 로직 (후에 토큰 관리하다보면 login or main 분기처리)
    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LogInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        },DURATION)
    }

    private fun isFirst() {
        lifecycleScope.launch {
            Log.e("TEST", viewModel.isFirstLaunch.toString())
        }
        //Log.e("Test" , loginViewModel.isFirstLaunch.toString())

    }



    companion object {
        private const val DURATION : Long = 2000
    }

}