package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
=======
import androidx.navigation.fragment.findNavController
>>>>>>> 241dde8136689360cc5627d795dd882bd9ea939c
import com.mument_android.R
import com.mument_android.app.presentation.ui.locker.adapter.LockerTabAdapter
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentLockerBinding>()
    private lateinit var lockerTabAdapter : LockerTabAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
<<<<<<< HEAD
        initAdapter()
        initTab()
    }

    private fun initAdapter() {
        val fragmentList = listOf(MyMumentFragment(), MyLikeFragment())
        lockerTabAdapter = LockerTabAdapter(this)
        lockerTabAdapter.fragment.addAll(fragmentList)

        binding.vpLocker.adapter = lockerTabAdapter

    }

    private fun initTab(){
        val tabLabel = listOf("나의 뮤멘트", "좋아요한 뮤멘트")
        TabLayoutMediator(binding.tlLocker, binding.vpLocker) {tab, position ->
            tab.text = tabLabel[position]
        }.attach()
=======
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_lockerFragment_to_mumentDetailFragment)
        }
>>>>>>> 241dde8136689360cc5627d795dd882bd9ea939c
    }
}