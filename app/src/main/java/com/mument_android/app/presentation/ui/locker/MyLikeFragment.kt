package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.adapter.LockerTimeAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMyLikeBinding
import timber.log.Timber

class MyLikeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyLikeBinding>()
    private val lockerViewModel: LockerViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyLikeBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivLockerList.isSelected = true
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = lockerViewModel
        lockerViewModel.fetchMyLikeList()

        setMyMumentListAdapter()
        settingRecyclerView()
        listBtnClickListener()
        gridBtnClickListener()
        filterBtnClickListener()
        fetchLikes()
    }

    private fun setGridServerConnection() {
        binding.rvLikeLinear.run {
            lockerViewModel.isGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
                adapter = LockerTimeAdapter(isGridLayout)
                (binding.rvLikeLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myLikeMuments.value?.data)
            }
        }
    }

    private fun setMyMumentListAdapter() {
        setGridServerConnection()
        lockerViewModel.myMuments.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
            when (it) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    binding.rvLikeLinear.adapter = LockerTimeAdapter(false)
                    initLikeEmpty(it.data?.size ?: 0)
                    //initMumentEmpty(0)
                    (binding.rvLikeLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myLikeMuments.value?.data)
                }
            }

        }

    }

    //좋아요 한 뮤멘트 엠티뷰
    //TODO: 필터 및 아이콘들 비활성화
    private fun initLikeEmpty(size: Int) {
        if (size == 0) {
            binding.clEmptyView.visibility = View.VISIBLE
        } else {
            binding.clEmptyView.visibility = View.GONE
        }
    }

    private fun listBtnClickListener() {
        binding.ivLockerList.setOnClickListener {
            lockerViewModel.changeLikeIsGridLayout(false)
            setMyMumentListAdapter()
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false

        }
    }

    private fun gridBtnClickListener() {
        binding.ivLockerGrid.setOnClickListener {
            lockerViewModel.changeLikeIsGridLayout(true)
            setGridServerConnection()
            binding.ivLockerList.isSelected = false
            binding.ivLockerGrid.isSelected = true
        }
    }

    private fun filterBtnClickListener() {
        binding.ivLockerFilter.setOnClickListener {
            LockerLikeFilterBottomSheetFragment.newInstance(
                lockerViewModel.checkedLikeTagList.value ?: listOf(),
                completeSelectListener = {
                    lockerViewModel.changeLikeCheckedTagList(it)
                }
            ).show(parentFragmentManager, "LockerLikeFilterBottomSheetFragment")
        }
    }

    private fun settingRecyclerView() {
        removeTag()
        lockerViewModel.checkedLikeTagList.observe(viewLifecycleOwner) {
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
                lockerViewModel.removeLikeCheckedList(tag)
            }

            lockerViewModel.checkedLikeTagList.observe(viewLifecycleOwner) {
                Timber.d("LikeTag: $it")
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

    private fun fetchLikes() {
        lockerViewModel.checkedLikeTagList.observe(viewLifecycleOwner) {
            lockerViewModel.fetchMyLikeList()
        }
    }


}