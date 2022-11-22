package com.mument_android.mypage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.mypage.MyPageViewModel
import com.mument_android.mypage.R
import com.mument_android.mypage.adapters.BlockUserManagementAdapter
import com.mument_android.mypage.adapters.NoticeAdapter
import com.mument_android.mypage.databinding.FragmentNoticeBinding

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

        binding.lifecycleOwner = this
        binding.myPageViewModel = myPageViewModel

        setNoticeRecyclerView()
    }


    //공지사항 리싸이클러뷰
    private fun setNoticeRecyclerView(){
        noticeAdapter = NoticeAdapter()
        binding.rvNotice.adapter = noticeAdapter

        myPageViewModel.noticeList.observe(viewLifecycleOwner){
            noticeAdapter.submitList(it)
        }
    }


}