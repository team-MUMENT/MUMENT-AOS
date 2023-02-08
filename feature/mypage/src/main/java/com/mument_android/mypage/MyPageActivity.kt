package com.mument_android.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.login.LogInActivity
import com.mument_android.login.ProfileSettingActivity
import com.mument_android.mypage.databinding.ActivityMyPageBinding
import com.mument_android.mypage.fragment.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : BaseActivity<ActivityMyPageBinding>(ActivityMyPageBinding::inflate) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.myPageViewModel = myPageViewModel

        intent.getBooleanExtra("alarm", false).let {
            if (it) {
                supportFragmentManager.commit() {
                    replace(R.id.fc_my_page, AlarmSettingFragment(), "view")
                    addToBackStack("view")
                }
            }
        }

        moveProfileSetting()

        transactionBtnEvent()
        clickListenerWebView()
        logoutBtnListener()
        moveUnregister()
        userInfoNetwork()
        backBtnEvent()
    }

    private fun moveProfileSetting() {
        binding.clProfile.setOnClickListener {
            Log.e("USER INFO", myPageViewModel.userInfo.value.toString())
            val intent = Intent(this, ProfileSettingActivity::class.java)
            intent.putExtra("nickname", myPageViewModel.userInfo.value?.userName)
            intent.putExtra("img", myPageViewModel.userInfo.value?.image)
            intent.putExtra("checkMyPage", 1)
            startActivity(intent)
        }
    }

    //각 카테고리 버튼 눌렀을 때 이동하는 함수
    private fun transactionBtnEvent() {
        val goNextPageBtn = mutableMapOf(
            binding.clAlarmSetting to AlarmSettingFragment(),
            binding.clBlockUserManagement to BlockUserManagementFragment(),
            binding.clNotice to NoticeFragment()
        )

        goNextPageBtn.forEach { (btn, view) ->
            btn.setOnClickListener {
                supportFragmentManager.commit() {
                    replace(R.id.fc_my_page, view, "view")
                    addToBackStack("view")
                }
            }
            myPageViewModel.isClickBtnEvent(true)
        }
    }

    //각 웹뷰로 이동
    private fun clickListenerWebView() {
        myPageViewModel.getWebView("mypage")
        myPageViewModel.getWebView.observe(this) {
            val faq = it.faq.toString()
            val contact = it.contact.toString()
            val appInfo = it.appInfo.toString()
            val introduction = it.introduction.toString()

            with(binding) {
                //자주묻는질문
                clFAQ.setOnClickListener {
                    initIntent(faq)
                }
                //문의하기
                clInquiry.setOnClickListener {
                    initIntent(contact)
                }
                //앱정보
                clAppInfo.setOnClickListener {
                    initIntent(appInfo)
                }
                //뮤멘트 소개
                clIntroduceMument.setOnClickListener {
                    initIntent(introduction)
                }
            }
        }

    }

    private fun userInfoNetwork() {
        myPageViewModel.userInfo()
        myPageViewModel.userInfo.observe(this) {
            binding.viewModel = it
        }
    }

    private fun initIntent(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun logoutBtnListener() {
        binding.tvMyPageLogout.setOnClickListener {
            MumentDialogBuilder()
                .setHeader(getString(R.string.logout_header))
                .setBody("")
                .setAllowListener("로그아웃") {
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setCancelListener {}
                .build()
                .show(supportFragmentManager, attributionTag)
        }
    }

    //회원탈퇴뷰 이동
    private fun moveUnregister() {
        binding.tvMyPageUnregister.setOnClickListener {
            supportFragmentManager.commit() {
                replace(R.id.fc_my_page, UnregisterFragment(), "unregister")
                addToBackStack("unregister")
            }
        }
    }

    private fun backBtnEvent() {
        binding.btnMyPageBack.setOnClickListener {
            onBackPressed()
        }
    }

}




