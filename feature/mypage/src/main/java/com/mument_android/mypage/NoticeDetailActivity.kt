package com.mument_android.mypage

import android.os.Bundle
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.mypage.data.NoticeData
import com.mument_android.mypage.databinding.ActivityNoticeDetailBinding

class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(ActivityNoticeDetailBinding::inflate) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.myPageViewModel = myPageViewModel

        getNoticeData()
        backBtnListener()
    }

    //공지사항 데이터 받아오기
    private fun getNoticeData() {
        val noticeList = intent.getParcelableExtra<NoticeData>("NoticeData")

        if (noticeList != null) {
            binding.tvNoticeDetailItemTitle.text = noticeList.title
            binding.tvNoticeDetailItemDate.text = noticeList.created_at
            binding.tvNoticeDetailContent.text = noticeList.content
        }
    }

    private fun backBtnListener() {
        binding.btnNoticeDetailBack.setOnClickListener {
            onBackPressed()
        }
    }

}
