package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.adapter.LockerTimeAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMyMumentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MyMumentFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyMumentBinding>()
    private val lockerViewModel: LockerViewModel by viewModels( ownerProducer = { requireParentFragment() } )
    private val viewModel: LockerViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyMumentBinding.inflate(inflater, container, false). run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivLockerList.isSelected = true
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = lockerViewModel
        setMyMumentListAdapter()
        emptyBtnClick()
        settingRecyclerView()
        listBtnClickListener()
        gridBtnClickListener()
        filterBtnClickListener()

    }

    private fun setGridServerConnection() {
        binding.rvMumentLinear.run {
            lockerViewModel.isGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
                adapter = LockerTimeAdapter(isGridLayout)
                (binding.rvMumentLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myMuments.value?.data)
                //(adapter as LockerTimeAdapter).submitList(viewModel.myMuments.value)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setMyMumentListAdapter()
    }

    private fun setMyMumentListAdapter() {
        setGridServerConnection()
        lockerViewModel.myMuments.launchWhenCreated(viewLifecycleOwner.lifecycleScope){
            when(it){
                is ApiResult.Loading -> {

                }
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    binding.rvMumentLinear.adapter = LockerTimeAdapter(false)
                    initMumentEmpty(it.data?.size ?: 0)
                    //initMumentEmpty(0)
                    (binding.rvMumentLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myMuments.value?.data)
                }
            }

        }

    }

    /*initMumentEmpty(it.size)
    (adapter as LockerTimeAdapter).submitList(it)*/

    //TODO: 필터 및 아이콘들 비활성화
    private fun initMumentEmpty(size : Int) {
        if(size == 0) {
            binding.clEmptyView.visibility = View.VISIBLE

        } else {
            binding.clEmptyView.visibility = View.GONE
        }
    }

    private fun emptyBtnClick() {
        binding.clEmptyRecord.setOnClickListener {
            //TODO: 기록하기 뷰로 이동
        }
    }

    private fun listBtnClickListener() {
        binding.ivLockerList.setOnClickListener {
            lockerViewModel.changeIsGridLayout(false)
            setMyMumentListAdapter()
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false

        }
    }

    private fun gridBtnClickListener() {
        binding.ivLockerGrid.setOnClickListener {
            lockerViewModel.changeIsGridLayout(true)
            setGridServerConnection()
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
}