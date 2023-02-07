package com.mument_android.mypage

import android.os.Bundle
import androidx.activity.viewModels
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.mypage.databinding.ActivityNoticeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(ActivityNoticeDetailBinding::inflate) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        getNoticeData()
        backBtnListener()
    }

    //공지사항 데이터 받아오기
    private fun getNoticeData() {
        val id = intent.getIntExtra("id", -1)
        myPageViewModel.fetchNoticeDetail(id)
        collectFlowWhenStarted(myPageViewModel.noticeDetail) {
            when (it) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    binding.tvNoticeDetailItemTitle.text = it.datas.title
                    binding.tvNoticeDetailItemDate.text = it.datas.createAt
                    binding.tvNoticeDetailContent.text = it.datas.content
                }
                else -> {}
            }
        }
    }

    private fun backBtnListener() {
        binding.btnNoticeDetailBack.setOnClickListener {
            onBackPressed()
        }
    }
}
