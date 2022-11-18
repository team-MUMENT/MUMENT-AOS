package com.example.mypage

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypage.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextUnderLine()
    }

    // 텍스트 밑줄 설정하는 함수
    private fun setTextUnderLine(){
        binding.tvMyPageLogout.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvMyPageWithdrawal.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

}