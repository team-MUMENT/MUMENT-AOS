package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.mument_android.R
import com.mument_android.app.presentation.ui.locker.adapter.LockerTabAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentLockerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockerFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentLockerBinding>()
    private lateinit var lockerTabAdapter : LockerTabAdapter
    private val viewModel : LockerViewModel by viewModels()
    private var lockerFilterBottomSheetfragment = LockerFilterBottomSheetFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivLockerList.isSelected = true
        initAdapter()
        initTab()
        listBtnClickListener()
        gridBtnClickListener()
        filterBtnClickListener()
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

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_lockerFragment_to_mumentDetailFragment)
        }

    }


    private fun listBtnClickListener() {
        binding.ivLockerList.setOnClickListener {
            viewModel.changeIsGridLayout(false)
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false

        }
    }

    private fun gridBtnClickListener() {
        binding.ivLockerGrid.setOnClickListener {
            viewModel.changeIsGridLayout(true)
            binding.ivLockerList.isSelected = false
            binding.ivLockerGrid.isSelected = true
        }
    }

    private fun filterBtnClickListener() {
        binding.ivLockerFilter.setOnClickListener {
            lockerFilterBottomSheetfragment.show(parentFragmentManager, lockerFilterBottomSheetfragment.tag)
        }
    }

}