package com.mument_android.locker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.locker.adapters.LockerTabAdapter
import com.mument_android.locker.databinding.FragmentLockerBinding
import com.mument_android.locker.viewmodels.LockerViewModel
import com.mument_android.mypage.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockerFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentLockerBinding>()
    private lateinit var lockerTabAdapter: LockerTabAdapter
    private val lockerViewModel: LockerViewModel by viewModels()
    private val viewModel: LockerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner


        imageSet()
        navToMyPage()
        initAdapter()
        initTab()
    }

    override fun onResume() {
        super.onResume()
        isMumentView()
    }

    private fun isMumentView() {
        if(!viewModel.isMument) {
            binding.vpLocker.doOnPreDraw {
                binding.vpLocker.currentItem = 1
            }
        }
    }

    private fun initAdapter() {
        val fragmentList = listOf(MyMumentFragment(), MyLikeFragment())
        lockerTabAdapter = LockerTabAdapter(this)
        lockerTabAdapter.fragment.addAll(fragmentList)
        binding.vpLocker.adapter = lockerTabAdapter
    }

    private fun initTab() {
        val tabLabel = listOf("나의 뮤멘트", "좋아요한 뮤멘트")
        TabLayoutMediator(binding.tlLocker, binding.vpLocker) { tab, position ->
            tab.text = tabLabel[position]
        }.attach()

    }

    private fun imageSet() {
        binding.ivProfile.load("https://mument.s3.ap-northeast-2.amazonaws.com/user/angdroid/%EC%95%88%EB%93%9C.png") {
            crossfade(true)
            this.transformations(CircleCropTransformation())
        }
    }

    //마이페이지 이동
    private fun navToMyPage() {
        binding.ivProfile.setOnClickListener {
            val intent = Intent(context, MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}