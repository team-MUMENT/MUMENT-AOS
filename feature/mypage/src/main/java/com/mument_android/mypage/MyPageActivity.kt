package com.mument_android.mypage

import android.os.Bundle
import androidx.activity.viewModels
import android.content.Intent
import androidx.fragment.app.commit
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.core_dependent.ui.MumentDialog
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.login.LogInActivity
import com.mument_android.mypage.databinding.ActivityMyPageBinding
import com.mument_android.mypage.fragment.*

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(ActivityMyPageBinding::inflate) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.myPageViewModel = myPageViewModel
        transactionBtnEvent()
        clickListenerWebView()
        logoutBtnListener()
        moveUnregister()
    }

    //각 카테고리 버튼 눌렀을 때 이동하는 함수
    private fun transactionBtnEvent() {

        val goNextPageBtn = mutableMapOf(
            binding.btnMyPageGoProfile to ProfileSettingFragment(),
            binding.btnMyPageGoAlarmSetting to AlarmSettingFragment(),
            binding.btnMyPageGoBlockUserManagement to BlockUserManagementFragment(),
            binding.btnMyPageGoNotice to NoticeFragment()
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
        with(binding) {
            //자주묻는질문
            btnMyPageGoFAQ.setOnClickListener {
                initIntent("https://www.naver.com/")
            }
            //문의하기
            btnMyPageGoInquiry.setOnClickListener {
                initIntent("https://www.naver.com/")
            }
            //앱정보
            btnMyPageGoAppInfo.setOnClickListener {
                initIntent("https://www.naver.com/")
            }
            //뮤멘트 소개
            btnMyPageGoIntroduceMument.setOnClickListener {
                initIntent("https://www.naver.com/")
            }
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
                .setOption(MumentDialog.DIALOG_LOGOUT)
                .setAllowListener {
                    finish()
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
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

}




