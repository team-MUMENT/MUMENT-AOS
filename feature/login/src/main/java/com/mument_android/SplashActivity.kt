package com.mument_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlow
import com.mument_android.login.LogInActivity
import com.mument_android.login.LogInViewModel
import com.mument_android.login.databinding.ActivitySplashBinding
import com.mument_android.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val viewModel: LogInViewModel by viewModels()
    @Inject
    lateinit var dataStoreManager: DataStoreManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplash()
        //dataStoreManager = DataStoreManager(this)

    }

    //스플래시 -> 우선은 로그인으로 가는 로직 (후에 토큰 관리하다보면 login or main 분기처리)
    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            isFirst()
        }, DURATION)
    }

    private fun isFirst() {
        collectFlow(dataStoreManager.isFirstFlow) {
            Log.e("datastore", "$it")
            delay(1000)
            if(it == null) {
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }
            finish()
        }

    }


    companion object {
        private const val DURATION : Long = 2000
    }

}