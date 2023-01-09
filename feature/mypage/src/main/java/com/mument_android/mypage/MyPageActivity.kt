package com.mument_android.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.base.WebViewActivity
import com.mument_android.mypage.databinding.ActivityMyPageBinding
import com.mument_android.mypage.fragment.*

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.myPageViewModel = myPageViewModel

        transactionBtnEvent()
        clickListenerWebView()
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
                    replace(R.id.fc_my_page, view)
                }
            }
            myPageViewModel.isClickBtnEvent(true)
        }
    }

    //각 웹뷰로 이동
    private fun clickListenerWebView(){
        with(binding){
            //자주묻는질문
            btnMyPageGoFAQ.setOnClickListener {
                initIntent("https://www.naver.com/")
            }
            //문의하기
            btnMyPageGoInquiry.setOnClickListener{
                initIntent("https://www.naver.com/")
            }
            //앱정보
            btnMyPageGoAppInfo.setOnClickListener{
                initIntent("https://www.naver.com/")
            }
            //뮤멘트 소개
            btnMyPageGoIntroduceMument.setOnClickListener{
                initIntent("https://www.naver.com/")
            }
        }
    }

    private fun initIntent(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        ContextCompat.startActivity(this, intent, null)
    }

    //회원탈퇴뷰 이동
    private fun moveUnregister() {
        binding.tvMyPageUnregister.setOnClickListener {
            supportFragmentManager.commit() {
                replace(R.id.fc_my_page, UnregisterFragment())
            }
        }
    }

}




