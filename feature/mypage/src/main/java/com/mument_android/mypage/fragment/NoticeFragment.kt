package com.mument_android.mypage.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.domain.entity.mypage.NoticeListEntity
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.NoticeDetailActivity
import com.mument_android.mypage.adapters.NoticeAdapter
import com.mument_android.mypage.databinding.FragmentNoticeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeFragment : Fragment() {

    private lateinit var noticeAdapter: NoticeAdapter
    private var binding by AutoClearedValue<FragmentNoticeBinding>()
    private val myPageViewModel: MyPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNoticeBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        setNoticeRecyclerView()
        backBtnListener()
    }

    //공지사항 리싸이클러뷰
    private fun setNoticeRecyclerView() {
        noticeAdapter = NoticeAdapter(::itemClick)
        binding.rvNotice.adapter = noticeAdapter
        myPageViewModel.fetchNoticeList()
        collectFlowWhenStarted(myPageViewModel.noticeList) {
            when (it) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    noticeAdapter.submitList(it.data)
                }
                else -> {}
            }
        }
    }
    private fun itemClick(data:NoticeListEntity){
        val intent = Intent(requireActivity(), NoticeDetailActivity::class.java).apply {
            putExtra("id",data.id)
        }
        startActivity(intent)
    }

    private fun backBtnListener() {
        binding.btnNoticeBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}