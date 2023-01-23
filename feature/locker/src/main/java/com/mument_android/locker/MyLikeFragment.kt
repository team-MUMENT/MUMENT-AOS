package com.mument_android.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.ext.launchWhenCreated
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.locker.adapters.FilterBottomSheetSelectedAdapter
import com.mument_android.locker.adapters.LockerTimeAdapter
import com.mument_android.locker.databinding.FragmentMyLikeBinding
import com.mument_android.locker.filter.LockerLikeFilterBottomSheetFragment
import com.mument_android.locker.viewmodels.LockerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyLikeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyLikeBinding>()
    private val lockerViewModel: LockerViewModel by viewModels()
    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

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

        settingRecyclerView()
        listBtnClickListener()
        gridBtnClickListener()
        filterBtnClickListener()
        fetchLikes()
    }

    override fun onResume() {
        super.onResume()

        setMyMumentListAdapter()

    }

    private fun setGridServerConnection() {
        binding.rvLikeLinear.run {
//            lockerViewModel.isLikeGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isGridLayout ->
//                adapter = LockerTimeAdapter(isGridLayout)
//                (binding.rvLikeLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myLikeMuments.value?.data)


            lockerViewModel.isLikeGridLayout.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { isLikeGridLayout ->
                adapter = LockerTimeAdapter(isLikeGridLayout, showDetailListener = { mumentId, musicInfo ->
                    showMumentDetail(mumentId, musicInfo)
                }, object: LikeMumentListener {
                    override fun likeMument(mumetId: String) {
                        lockerViewModel.likeMument(mumetId)
                    }

                    override fun cancelLikeMument(mumetId: String) {
                        lockerViewModel.cancelLikeMument(mumetId)
                    }
                })
                (adapter as LockerTimeAdapter).submitList(lockerViewModel.myLikeMuments.value?.data)

            }
        }
    }

    private fun setMyMumentListAdapter() {
        // setGridServerConnection()
        lockerViewModel.myLikeMuments.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
            when (it) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    binding.rvLikeLinear.adapter =
                        LockerTimeAdapter(lockerViewModel.isLikeGridLayout.value,
                            showDetailListener =  { mumentId, musicInfo ->
                                showMumentDetail(mumentId, musicInfo)
                        },object: LikeMumentListener {
                            override fun likeMument(mumetId: String) {
                                lockerViewModel.likeMument(mumetId)
                            }

                            override fun cancelLikeMument(mumetId: String) {
                                lockerViewModel.cancelLikeMument(mumetId)
                            }
                        })

                    initLikeEmpty(it.data?.size ?: 0)
                    (binding.rvLikeLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myLikeMuments.value?.data)
                }
                else -> {}
            }

        }

    }

    //좋아요 한 뮤멘트 엠티뷰
    //TODO: 필터 및 아이콘들 비활성화
    private fun initLikeEmpty(size: Int) {
        if (size == 0) {
            binding.clFilterResultNull.visibility = View.VISIBLE
        } else {
            binding.clFilterResultNull.visibility = View.GONE
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
            setGridServerConnection()
            lockerViewModel.changeLikeIsGridLayout(true)

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


    private fun showMumentDetail(mumentId: String, musicInfo: MusicInfoEntity) {
        mumentDetailNavigatorProvider.moveMumentDetail(mumentId, musicInfo)
    }

    companion object {
        const val MUMENT_ID = "MUMENT_ID"
    }
}