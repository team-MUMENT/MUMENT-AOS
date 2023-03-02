package com.mument_android.locker

import android.content.Intent
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayout
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
        userInfoNetwork()
    }

    override fun onResume() {
        super.onResume()
        isMumentView()
    }

    private fun isMumentView() {
        if(!viewModel.isMument) {
            binding.vpLocker.doOnPreDraw {
                binding.vpLocker.currentItem = 1
                binding.tlLocker.changeTabsFont(1)
            }
        } else {
            binding.tlLocker.changeTabsFont(0)
        }
    }

    private fun userInfoNetwork() {
        lockerViewModel.userInfo()
        lockerViewModel.userInfo.observe(viewLifecycleOwner) {
            binding.viewModel = it
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

        binding.tlLocker.addOnTabSelectedListener(tabLayoutOnPageChangeListener)

    }

    private val tabLayoutOnPageChangeListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tabItem: TabLayout.Tab?) {}

        override fun onTabUnselected(tabItem: TabLayout.Tab?) {}

        override fun onTabSelected(tabItem: TabLayout.Tab?) {
            tabItem?.position?.let {
                binding.tlLocker.changeTabsFont(it)
            }
            if(tabItem?.position == 1) {

            }
        }
    }

    fun TabLayout.changeTabsFont(selectPosition: Int) {
        val vg = this.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            vgTab.forEachIndexed { index, _ ->
                val tabViewChild = vgTab.getChildAt(index)
                if (tabViewChild is TextView) {
                    tabViewChild.setTextBold(j == selectPosition)
                }
            }
        }
    }

    fun TextView.setTextBold(isBold: Boolean) {
        this.typeface = ResourcesCompat.getFont(this.context,if(isBold) R.font.notosans_semibold else R.font.notosans_medium)
    }

    private fun imageSet() {
        viewModel.profileImage.observe(viewLifecycleOwner) {
            binding.ivProfile.load(it) {
                crossfade(true)
                this.transformations(CircleCropTransformation())
            }
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