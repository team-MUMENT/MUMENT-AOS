package com.example.mypage

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mypage.databinding.ActivityMyPageBinding
import com.mument_android.core_dependent.base.BaseActivity

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

}


