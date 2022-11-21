package com.mument_android.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.mument_android.locker.adapters.FilterBottomSheetSelectedAdapter
import com.mument_android.locker.adapters.LockerTimeAdapter
import com.mument_android.locker.filter.LockerFilterBottomSheetFragment
import com.mument_android.locker.viewmodels.LockerViewModel
import com.mument_android.core_dependent.ext.launchWhenCreated
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.locker.MyLikeFragment.Companion.MUMENT_ID
import com.mument_android.locker.databinding.FragmentMyMumentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyMumentFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyMumentBinding>()
    private val lockerViewModel: LockerViewModel by viewModels()
    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

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
                adapter = LockerTimeAdapter(isGridLayout, showDetailListener = { showMumentDetail(it) }, object :
                    LikeMumentListener {
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
                    binding.rvMumentLinear.adapter = LockerTimeAdapter(lockerViewModel.isGridLayout.value, showDetailListener = { showMumentDetail(it) }, object :
                        LikeMumentListener {
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
                else -> {}
            }
        }
    }

    //TODO: 필터 및 아이콘들 비활성화
    private fun initMumentEmpty(size : Int) {
        if(size == 0) {
            binding.clFilterResultNull.visibility = View.VISIBLE
        } else {
            binding.clFilterResultNull.visibility = View.GONE
        }
    }

    private fun emptyBtnClick() {
        binding.clEmptyRecord.setOnClickListener {
            //TODO: 기록하기 뷰로 이동 -> 데모데이 때 X
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
        //val bundle = Bundle().also { it.putString(MUMENT_ID, mumentId) }
        mumentDetailNavigatorProvider.moveMumentDetail(mumentId)
        //findNavController().navigate(R.id.action_lockerFragment_to_mumentDetailFragment, bundle)
    }
}