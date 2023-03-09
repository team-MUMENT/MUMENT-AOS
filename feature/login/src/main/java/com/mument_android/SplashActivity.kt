package com.mument_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.angdroid.navigation.MainHomeNavigatorProvider
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.login.LogInActivity
import com.mument_android.login.LogInViewModel
import com.mument_android.login.databinding.ActivitySplashBinding
import com.mument_android.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val viewModel: LogInViewModel by viewModels()

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Inject
    lateinit var mainHomeNavigatorProvider: MainHomeNavigatorProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplash()
    }

    //스플래시 -> 우선은 로그인으로 가는 로직 (후에 토큰 관리하다보면 login or main 분기처리)
    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            isFirst()
        }, DURATION)
    }

    private fun isFirst() {
        lifecycleScope.launch {
            if (dataStoreManager.isFirstFlow.firstOrNull() == null) {
                Log.e("datastore", "onBoarding")
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                if (dataStoreManager.isFirstFlow.firstOrNull() == false) {
                    dataStoreManager.writeIsFirst(true)
                }
                viewModel.isExistProfile()
                viewModel.isExist.collectLatest { exist ->
                    Log.e("Why?", exist.toString())
                    if (exist == true) {
                        moveToMainActivity()
                        finish()
                    } else if(exist == false){
                        val intent = Intent(this@SplashActivity, LogInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun moveToMainActivity() {
        mainHomeNavigatorProvider.profileSettingToMain()
    }

    companion object {
        private const val DURATION: Long = 1200
    }

}