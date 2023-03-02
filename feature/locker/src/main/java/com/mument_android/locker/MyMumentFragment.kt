package com.mument_android.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.locker.adapters.FilterBottomSheetSelectedAdapter
import com.mument_android.locker.adapters.LockerTimeAdapter
import com.mument_android.locker.databinding.FragmentMyMumentBinding
import com.mument_android.locker.filter.LockerFilterBottomSheetFragment
import com.mument_android.locker.viewmodels.LockerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyMumentFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMyMumentBinding>()
    private val lockerViewModel: LockerViewModel by activityViewModels()

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyMumentBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = lockerViewModel
        settingRecyclerView()
        listBtnClickListener()
        setGridServerConnection()
        gridBtnClickListener()
        filterBtnClickListener()
        fetchMuments()
        setMyMumentListAdapter()
    }

    override fun onResume() {
        super.onResume()

        if (lockerViewModel.isGridLayout.value) {
            binding.ivLockerList.isSelected = false
            binding.ivLockerGrid.isSelected = true
        } else {
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false
        }

        lockerViewModel.isMument.value = true
    }

    private fun setGridServerConnection() {
        collectFlowWhenStarted(lockerViewModel.isGridLayout) { isGridLayout ->
                binding.rvMumentLinear.run {
                    adapter = LockerTimeAdapter(
                        isGridLayout,
                        showDetailListener = { mumentId, musicInfo ->
                            showMumentDetail(mumentId, musicInfo)
                        },
                        likeMumentListener = object : LikeMumentListener {
                            override fun likeMument(mumetId: String) {
                                lockerViewModel.likeMument(mumetId)
                            }

                            override fun cancelLikeMument(mumetId: String) {
                                lockerViewModel.cancelLikeMument(mumetId)
                            }
                        }
                    )
                    (binding.rvMumentLinear.adapter as LockerTimeAdapter).submitList(lockerViewModel.myMuments.value?.data)
                }
        }
    }

    private fun setMyMumentListAdapter() {
        collectFlowWhenStarted(lockerViewModel.myMuments) { result ->
            when (result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    binding.rvMumentLinear.adapter = LockerTimeAdapter(
                        lockerViewModel.isGridLayout.value,
                        showDetailListener = { mumentId, musicInfo ->
                            showMumentDetail(mumentId, musicInfo)
                        },
                        likeMumentListener = object : LikeMumentListener {
                            override fun likeMument(mumetId: String) {
                                lockerViewModel.likeMument(mumetId)
                            }

                            override fun cancelLikeMument(mumetId: String) {
                                lockerViewModel.cancelLikeMument(mumetId)
                            }
                        }
                    )
                    //binding.rvMumentLinear.adapter = LockerTimeAdapter(lockerViewModel.isGridLayout.value)
                    binding.rvMumentLinear.adapter = LockerTimeAdapter(
                        lockerViewModel.isGridLayout.value,
                        showDetailListener = { mumentId, musicInfo ->
                            showMumentDetail(mumentId, musicInfo)
                        },
                        likeMumentListener = object : LikeMumentListener {
                            override fun likeMument(mumetId: String) {
                                lockerViewModel.likeMument(mumetId)
                            }

                            override fun cancelLikeMument(mumetId: String) {
                                lockerViewModel.cancelLikeMument(mumetId)
                            }
                        }
                    )

                    initMumentEmpty(result.data?.size ?: 0)
                    (binding.rvMumentLinear.adapter as LockerTimeAdapter).submitList(
                        lockerViewModel.myMuments.value?.data
                    )
                }
                else -> {}
            }

            //binding.ivLockerList.isSelected = true
            //binding.ivLockerGrid.isSelected = false
        }
    }


    //TODO: 필터 및 아이콘들 비활성화
    private fun initMumentEmpty(size: Int) {
        if (size == 0) {
            if (binding.ivLockerFilter.isSelected) {
                binding.clFilterResultNull.visibility = View.VISIBLE
                binding.clEmptyView.visibility = View.GONE
            } else {
                binding.clEmptyView.visibility = View.VISIBLE
                binding.clFilterResultNull.visibility = View.GONE
            }
        } else {
            binding.clFilterResultNull.visibility = View.GONE
            binding.clEmptyView.visibility = View.GONE
        }
    }


    private fun listBtnClickListener() {
        binding.ivLockerList.setOnClickListener {
            FirebaseAnalyticsUtil.firebaseLog(
                "use_grid_my_mument",
                "journey",
                "my_mument_list"
            )
            lockerViewModel.changeIsGridLayout(false)
            binding.ivLockerList.isSelected = true
            binding.ivLockerGrid.isSelected = false

        }
    }

    private fun gridBtnClickListener() {
        binding.ivLockerGrid.setOnClickListener {
            FirebaseAnalyticsUtil.firebaseLog(
                "use_grid_my_mument",
                "journey",
                "my_mument_grid"
            )
            lockerViewModel.changeIsGridLayout(true)
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

    private fun showMumentDetail(mumentId: String, musicInfo: MusicInfoEntity) {
        mumentDetailNavigatorProvider.moveLockerToMumentDetail(mumentId, musicInfo)
    }
}