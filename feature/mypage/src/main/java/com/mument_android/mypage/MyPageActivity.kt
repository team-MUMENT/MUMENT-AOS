package com.mument_android.mypage

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageButton
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.mypage.databinding.ActivityMyPageBinding
import com.mument_android.mypage.fragment.*

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.myPageViewModel = myPageViewModel
        setContentView(binding.root)

        setTextUnderLine()
    }

    // 텍스트 밑줄 설정하는 함수
    private fun setTextUnderLine() {
        binding.tvMyPageLogout.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvMyPageWithdrawal.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    //각 카테고리 버튼 눌렀을 때 이동하는 함수
    fun transactionBtnEvent(transactionView: View) {

        val goNextPageBtn = mutableMapOf(
            binding.btnMyPageGoProfile to ProfileSettingFragment(),
            binding.btnMyPageGoAlarmSetting to AlarmSettingFragment(),
            binding.btnMyPageGoBlockUserManagement to BlockUserManagementFragment(),
            binding.btnMyPageGoNotice to NoticeFragment()
        )

        supportFragmentManager.beginTransaction().add(R.id.fc_my_page, ProfileSettingFragment())
            .commit()
        val transaction = supportFragmentManager.beginTransaction()

        myPageViewModel.isClickBtnEvent(true)

        goNextPageBtn.forEach { (btn, view) ->
            when (transactionView) {
                btn -> transaction.replace(R.id.fc_my_page, view).commit()
            }
        }
    }

}


