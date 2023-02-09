package com.mument_android.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.angdroid.navigation.MainHomeNavigatorProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlow
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.domain.entity.sign.RequestKakaoData
import com.mument_android.login.databinding.ActivityLogInBinding
import com.mument_android.login.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LogInActivity : BaseActivity<ActivityLogInBinding>(ActivityLogInBinding::inflate) {
    private val viewModel: LogInViewModel by viewModels()
    @Inject
    lateinit var dataStoreManager: DataStoreManager
    @Inject
    lateinit var mainHomeNavigatorProvider: MainHomeNavigatorProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keyHash = Utility.getKeyHash(this)
        Log.e("kkkkkkkkkk:","$keyHash")
//        initView()
        initKakaoLogin()
        btnKakaoListener()
        getFcmToken()
        webLinkNetwork()
    }



    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {  //기기 토큰 얻어오는 코드
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
            // Get new FCM registration token
            val token = task.result
            val msg = token.toString()
            Log.e("TAG", msg)

            viewModel.fcmToken.value = token.toString()
        })
    }

    private fun initKakaoLogin() {
        val kakaoAppKey = "dcf1de7e11089f484ac873f0e833427d"
        KakaoSdk.init(this, kakaoAppKey)

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

    private fun btnKakaoListener() {
        binding.ivKakao.setOnClickListener {
            setKakaoBtnListener()
        }
    }



    private fun setKakaoBtnListener() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                shortToast("로그인 실패")
                getErrorLog(error)
            } else if (token != null) {
                UserApiClient.instance.me { _, error ->
                    Log.e("kakao access token :", token.accessToken)
                    val requestKakaoData = RequestKakaoData(
                        "kakao",
                        token.accessToken.toString(),
                        viewModel.fcmToken.value.toString()
                    )
                    viewModel.kakaoLogin(requestKakaoData)
                    viewModel.kakaoData.observe(this) {
                        if(it.accessToken != null && viewModel.isExist.value == true) {
                            moveToMainActivity()
                        } else if(it.accessToken != null && viewModel.isExist.value == false) {
                            startActivity(Intent(this, ProfileSettingActivity::class.java))
                            finish()
                        }
                    }
                }
            } else {
                shortToast("else")
            }


        }


        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                this,
                callback = callback
            )
        }
    }

    private fun getErrorLog(error: Throwable) {
        when {
            error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                Log.e("접근이 거부 됨(동의 취소)", "")
            }
            error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                Log.e("유효하지 않은 앱","")
            }
            error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                Log.e("인증 수단이 유효하지 않아 인증할 수 없는 상태","")
            }
            error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                Log.e("요청 파라미터 오류","")
            }
            error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                Log.e("유효하지 않은 scope ID","")
            }
            error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                Log.e("설정이 올바르지 않음(android key hash)","")
            }
            error.toString() == AuthErrorCause.ServerError.toString() -> {
                Log.e("서버 내부 에러","")
            }
            error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                Log.e("앱이 요청 권한이 없음","")
            }
            else -> { // Unknown
                Log.e("기타 에러","")
            }
        }
    }

    private fun initIntent(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        ContextCompat.startActivity(this, intent, null)
    }

    private fun moveToMainActivity() {
        mainHomeNavigatorProvider.profileSettingToMain()
    }

}