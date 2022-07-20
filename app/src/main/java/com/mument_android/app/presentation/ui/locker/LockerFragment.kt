package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mument_android.R
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.adapter.LockerTabAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentLockerBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LockerFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentLockerBinding>()
    private lateinit var lockerTabAdapter: LockerTabAdapter
    private val lockerViewModel : LockerViewModel by viewModels()
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
        binding.vpLocker.isUserInputEnabled = false
        //binding.ivLockerList.isSelected = true
        binding.lifecycleOwner = viewLifecycleOwner
        initAdapter()
        initTab()
        //listBtnClickListener()
        //gridBtnClickListener()
        //filterBtnClickListener()
        //settingRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        //removeTag()

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

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_lockerFragment_to_mumentDetailFragment)
        }
    }

/*
    private fun listBtnClickListener() {
        binding.ivLockerList.setOnClickListener {
            lockerViewModel.changeIsGridLayout(false)
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false

        }
    }

    private fun gridBtnClickListener() {
        binding.ivLockerGrid.setOnClickListener {
            lockerViewModel.changeIsGridLayout(true)
            binding.ivLockerList.isSelected = false
            binding.ivLockerGrid.isSelected = true
        }
    }

    private fun filterBtnClickListener() {
        binding.ivLockerFilter.setOnClickListener {
            LockerFilterBottomSheetFragment.newInstance()
                .show(parentFragmentManager, "LockerFilterBottomSheetFragment")
        }
    }


    private fun settingRecyclerView() {
        removeTag()
        viewModel.realTagList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.rvSelectedTags.visibility = View.GONE
            } else {
                binding.rvSelectedTags.visibility = View.VISIBLE
            }
        }
    }

    private fun removeTag() {
        binding.rvSelectedTags.run {
            adapter = FilterBottomSheetSelectedAdapter { tag, idx ->
                viewModel.removeCheckedList(tag)
            }

            viewModel.checkedTagList.observe(viewLifecycleOwner) {
                (adapter as FilterBottomSheetSelectedAdapter).submitList(it)
                if (it.isEmpty()) {
                    binding.rvSelectedTags.visibility = View.GONE
                    binding.ivLockerFilter.isSelected = false
                } else {
                    binding.rvSelectedTags.visibility = View.VISIBLE
                    binding.ivLockerFilter.isSelected = true
                }
            }
        }

    }

 */
}