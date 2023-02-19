package com.mument_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.angdroid.navigation.MainHomeNavigatorProvider
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlow
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.login.LogInActivity
import com.mument_android.login.LogInViewModel
import com.mument_android.login.databinding.ActivitySplashBinding
import com.mument_android.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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
        isFirst()
        newTokenNetwork()
    }

    private fun isFirst() {
        Log.e("12j4123", "${viewModel.isExist.value}")
        collectFlow(dataStoreManager.accessTokenFlow) {
            Log.e("bbbbbbbbbbb", "$it")
        }
        collectFlow(dataStoreManager.isFirstFlow) {
            Log.e("datastore", "$it")
            delay(1000)
            if (it == null) {
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
            } else {
                if(viewModel.isExist.value == true) {
                    moveToMainActivity()
                    Log.e("뭐지", "31324")
                }
                else {
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
                }
            }
            finish()
        }

    }

    private fun newTokenNetwork() {
        collectFlowWhenStarted(dataStoreManager.userIdFlow) {
            if (it.isNullOrEmpty()) viewModel.saveTestUserId()
        }
    }

    private fun moveToMainActivity() {
        mainHomeNavigatorProvider.profileSettingToMain()
    }


}