package com.mument_android.mypage

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.mypage.databinding.ActivityMyPageBinding

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    private val fragmentArray: ArrayList<Fragment> = arrayListOf(
        AlarmSettingFragment(),
        AlarmSettingFragment(),
        AlarmSettingFragment(),
        BlockUserManagementFragment(),
        AlarmSettingFragment(),
        BlockUserManagementFragment(),
        AlarmSettingFragment(),
        BlockUserManagementFragment()
    )


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
    fun transactionBtnEvent(view: View) {

        //버튼 Array
        val btnArray: ArrayList<AppCompatImageButton> = arrayListOf(
            binding.btnMyPageGoProfile,
            binding.btnMyPageGoAlarmSetting,
            binding.btnMyPageGoBlockUserManagement,
            binding.btnMyPageGoNotice,
            binding.btnMyPageGoFAQ,
            binding.btnMyPageGoInquiry,
            binding.btnMyPageGoAppInfo,
            binding.btnMyPageGoIntroduceMument
        )

        supportFragmentManager.beginTransaction().add(R.id.fc_my_page, fragmentArray[0])
            .commit()
        val transaction = supportFragmentManager.beginTransaction()

        myPageViewModel.isClickBtnEvent(true)
        when (view) {
            btnArray[0] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[0]).commit()
            btnArray[1] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[1]).commit()
            btnArray[2] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[2]).commit()
            btnArray[3] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[3]).commit()
            btnArray[4] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[4]).commit()
            btnArray[5] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[5]).commit()
            btnArray[6] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[6]).commit()
            btnArray[7] ->
                transaction.replace(R.id.fc_my_page, fragmentArray[7]).commit()
        }


    }

}


