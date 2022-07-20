package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
    private val viewModel: LockerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyLikeBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = lockerViewModel
        setMyMumentListAdapter()
        settingRecyclerView()
        listBtnClickListener()
        gridBtnClickListener()
        filterBtnClickListener()
    }

    private fun setMyMumentListAdapter() {
        binding.rvLikeLinear.run {
            lockerViewModel.isGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
                adapter = LockerTimeAdapter(isGridLayout)
                //(adapter as LockerTimeAdapter).submitList(viewModel.myMuments.value)
            }
        }
        lockerViewModel.myMuments.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
            /*
            when (it) {
                is ApiResult.Loading -> {

                }
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    binding.rvLikeLinear.adapter = LockerTimeAdapter(false)
                    initLikeEmpty(it.data?.size ?: 0)
                    //initMumentEmpty(0)
                    (binding.rvLikeLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myMuments.value?.data)
                    Timber.d("Test : ${lockerViewModel.myMuments.value?.data}")
                }
            }
            */

        }

    }

    /*
    private fun setMyMumentListAdapter() {
        binding.rvLikeLinear.run {
            viewModel.myMuments.observe(viewLifecycleOwner) {
                initLikeEmpty(it.size)
                (adapter as LockerTimeAdapter).submitList(it)
            }
            viewModel.isGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
                adapter = LockerTimeAdapter(isGridLayout)
                (adapter as LockerTimeAdapter).submitList(viewModel.myMuments.value)
            }
        }
    }

     */

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
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false

        }
    }

    private fun gridBtnClickListener() {
        binding.ivLockerGrid.setOnClickListener {
            lockerViewModel.changeLikeIsGridLayout(true)
            binding.ivLockerList.isSelected = false
            binding.ivLockerGrid.isSelected = true
        }
    }

    private fun filterBtnClickListener() {
        binding.ivLockerFilter.setOnClickListener {
            LockerLikeFilterBottomSheetFragment.newInstance()
                .show(parentFragmentManager, "LockerLikeFilterBottomSheetFragment")
        }
    }

    private fun settingRecyclerView() {
        removeTag()
        viewModel.likeRealTagList.observe(viewLifecycleOwner) {
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

            viewModel.checkedLikeTagList.observe(viewLifecycleOwner) {
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


}