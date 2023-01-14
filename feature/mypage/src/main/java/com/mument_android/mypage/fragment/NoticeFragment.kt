package com.mument_android.mypage.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.NoticeDetailActivity
import com.mument_android.mypage.R
import com.mument_android.mypage.adapters.BlockUserManagementAdapter
import com.mument_android.mypage.adapters.NoticeAdapter
import com.mument_android.mypage.data.NoticeData
import com.mument_android.mypage.databinding.FragmentNoticeBinding

class NoticeFragment : Fragment() {

    private lateinit var noticeAdapter: NoticeAdapter
    private var binding by AutoClearedValue<FragmentNoticeBinding>()
    private val myPageViewModel: MyPageViewModel by viewModels()
    private var noticeList = mutableListOf<NoticeData>()


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
        itemClickEvent()
    }


    //공지사항 리싸이클러뷰
    private fun setNoticeRecyclerView() {
        noticeAdapter = NoticeAdapter()
        binding.rvNotice.adapter = noticeAdapter

        noticeList.add(
            NoticeData(
                0,
                "공지사항1",
                "공지사항 1 내용입니다.공지사항 1 내용입니다.공지사항 1 내용입니다.공지사항 1 내용입니다.공지사항 1 내용입니다.공지사항 1 내용입니다. \"https://www.naver.com/\"",
                "2023-02-02"
            )
        )
        noticeAdapter.submitList(noticeList)
    }

    //아이템 클릭 리스너
    private fun itemClickEvent() {
        noticeAdapter.setItemClickListener(object : NoticeAdapter.OnItemClickListener {
            override fun onClick(data: NoticeData) {
                val intent = Intent(requireContext(), NoticeDetailActivity::class.java).apply {
                    putExtra("NoticeData", data)
                }
                startActivity(intent)
            }
        })
    }
}