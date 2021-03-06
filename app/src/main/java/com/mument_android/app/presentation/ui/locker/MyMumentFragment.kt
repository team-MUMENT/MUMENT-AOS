package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.detail.mument.MumentDetailFragment.Companion.FROM_LOCKER
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.adapter.LockerTimeAdapter
import com.mument_android.app.presentation.ui.locker.filter.LockerFilterBottomSheetFragment
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMyMumentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyMumentFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyMumentBinding>()
    private val lockerViewModel: LockerViewModel by viewModels()

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

        emptyBtnClick()
        settingRecyclerView()
        listBtnClickListener()
        gridBtnClickListener()
        filterBtnClickListener()
        fetchMuments()

    }

    override fun onResume() {
        super.onResume()
        setMyMumentListAdapter()
    }

    private fun setGridServerConnection() {
        binding.rvMumentLinear.run {
            lockerViewModel.isGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
                adapter = LockerTimeAdapter(isGridLayout, showDetailListener = { showMumentDetail(it) }, object : LikeMumentListener{
                    override fun likeMument(mumetId: String) {
                        lockerViewModel.likeMument(mumetId)
                    }

                    override fun cancelLikeMument(mumetId: String) {
                        cancelLikeMument(mumetId)
                    }
                })
                (binding.rvMumentLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myMuments.value?.data)
            }
        }
    }

    private fun setMyMumentListAdapter() {
        //setGridServerConnection()
        lockerViewModel.myMuments.launchWhenCreated(viewLifecycleOwner.lifecycleScope){
            when(it){
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {

                    //binding.rvMumentLinear.adapter = LockerTimeAdapter(lockerViewModel.isGridLayout.value)
                    binding.rvMumentLinear.adapter = LockerTimeAdapter(lockerViewModel.isGridLayout.value, showDetailListener = { showMumentDetail(it) }, object : LikeMumentListener{
                        override fun likeMument(mumetId: String) {
                            lockerViewModel.likeMument(mumetId)
                        }

                        override fun cancelLikeMument(mumetId: String) {
                            lockerViewModel.cancelLikeMument(mumetId)
                        }
                    })

                    initMumentEmpty(it.data?.size ?: 0)
                    (binding.rvMumentLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myMuments.value?.data)
                }
            }
        }
    }

    //TODO: ?????? ??? ???????????? ????????????
    private fun initMumentEmpty(size : Int) {
        if(size == 0) {
            binding.clFilterResultNull.visibility = View.VISIBLE
        } else {
            binding.clFilterResultNull.visibility = View.GONE
        }
    }

    private fun emptyBtnClick() {
        binding.clEmptyRecord.setOnClickListener {
            //TODO: ???????????? ?????? ?????? -> ???????????? ??? X
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
            LockerFilterBottomSheetFragment.newInstance(
                lockerViewModel.checkedTagList.value ?: listOf(),
                completeSelectListener = {
                    lockerViewModel.changeCheckedTagList(it)
                }
            ).show(parentFragmentManager, "LockerFilterBottomSheetFragment")
        }
    }


    private fun settingRecyclerView() {
        removeTag()
        lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
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
                lockerViewModel.removeCheckedList(tag)
            }

            lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
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

    private fun fetchMuments() {
        lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
            lockerViewModel.fetchMyMumentList()
        }
    }

    private fun showMumentDetail(mumentId: String) {
        val action = LockerFragmentDirections.actionLockerFragmentToLockerMumentDetailFragment(mumentId)
        findNavController().navigate(action)
    }
}