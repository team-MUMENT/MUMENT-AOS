package com.mument_android.app.presentation.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetAdapter
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.ViewUtils.snackBar
import com.mument_android.databinding.FragmentLockerFilterBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LockerFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding by AutoClearedValue<FragmentLockerFilterBottomSheetBinding>()
    private val lockerViewModel: LockerViewModel by viewModels()
    private lateinit var filterBottomSheetAdapterImpress: FilterBottomSheetAdapter
    private lateinit var filterBottomSheetAdpaterEmotion: FilterBottomSheetAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerFilterBottomSheetBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clFilterBottomSheet.layoutParams.height =
            resources.displayMetrics.heightPixels * 93 / 100
        binding.executePendingBindings()

        setEmotionalList()
        resetClickListener()
        setSelectedTag()
        closeBtnListener()

    }

    private fun setEmotionalList() {
        filterBottomSheetAdapterImpress = FilterBottomSheetAdapter(
            requireContext(),
            object : FilterBottomSheetAdapter.FilterTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    lockerViewModel.addCheckedList(tag)
                    filterBottomSheetAdapterImpress.selectedTags.add(tag)

                    lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
                        Timber.d("${it}")
                    }

                }

                override fun removeCheckedTag(tag: TagEntity) {
                    filterBottomSheetAdapterImpress.selectedTags.remove(tag)
                    lockerViewModel.removeCheckedList(tag)
                    lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
                        Timber.d("${it}")
                    }
                }

                override fun alertMaxCount() {
                    requireContext().snackBar(
                        binding.clFilterBottomSheet,
                        "태그는 최대 3개까지 선택할 수 있어요"
                    )
                    Timber.d("snackBar test")
                }

            }
        )

        filterBottomSheetAdpaterEmotion = FilterBottomSheetAdapter(requireContext(),
            object : FilterBottomSheetAdapter.FilterTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    filterBottomSheetAdpaterEmotion.selectedTags.add(tag)
                    lockerViewModel.addCheckedList(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    filterBottomSheetAdpaterEmotion.selectedTags.remove(tag)
                    lockerViewModel.removeCheckedList(tag)
                }

                override fun alertMaxCount() {
                    requireContext().snackBar(
                        binding.clFilterBottomSheet,
                        "태그는 최대 3개까지 선택할 수 있어요"
                    )
                    Timber.d("snackBar test")
                }

            }
        )

        with(binding.rvEmotion) {
            setItemDecoration(this)
            setEmotionalRvFlexBoxLayout()
        }

        with(binding.rvImpressive) {
            setItemDecoration(this)
            setImpressiveRvFlexBoxLayout()
        }

    }

    private fun resetClickListener() {
        binding.tvClearAll.setOnClickListener {
            lockerViewModel.resetCheckedList()
        }
    }

    private fun closeBtnListener() {
        binding.ivFilterDelete.setOnClickListener {
            dismiss()
        }
    }

    private fun setSelectedTag() {
        binding.rvSelectedTags.run {
            adapter = FilterBottomSheetSelectedAdapter { tag, idx ->
                lockerViewModel.removeCheckedList(tag)
                syncSelectedTags(filterBottomSheetAdpaterEmotion.currentList.indexOf(tag))
                syncSelectedTags(filterBottomSheetAdapterImpress.currentList.indexOf(tag))
            }

            lockerViewModel.checkedTagList.observe(viewLifecycleOwner) {
                (adapter as FilterBottomSheetSelectedAdapter).submitList(it)
            }
        }
    }

    private fun syncSelectedTags(position: Int) {
        binding.rvEmotion.let { recyclerview ->
            val view = recyclerview[position]
            val viewHolder = recyclerview.getChildViewHolder(view)
            Timber.e("data position ${position}")
            Timber.e("view position ${viewHolder.absoluteAdapterPosition}")
            (viewHolder as FilterBottomSheetAdapter.BottomSheetFilterHolder).binding.cbTag.isChecked =
                false
        }

        binding.rvImpressive.let { recyclerView ->
            val view = recyclerView[position]
            val viewHolder = recyclerView.getChildViewHolder(view)
            (viewHolder as FilterBottomSheetAdapter.BottomSheetFilterHolder).binding.cbTag.isChecked =
                false

        }
    }

    //add code
    private fun setItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(
            RecyclerviewItemDivider(
                7.dpToPx(requireContext()),
                5.dpToPx(requireContext()),
                RecyclerviewItemDivider.IS_GRIDLAYOUT
            )
        )
    }

    private fun setImpressiveRvFlexBoxLayout() {
        with(binding.rvImpressive) {
            FlexboxLayoutManager(context).apply {
                flexWrap = com.google.android.flexbox.FlexWrap.WRAP
                flexDirection = com.google.android.flexbox.FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = filterBottomSheetAdapterImpress
            }
            filterBottomSheetAdapterImpress.submitList(
                ImpressiveTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) }
            )
        }
    }

    private fun setEmotionalRvFlexBoxLayout() {
        with(binding.rvEmotion) {
            FlexboxLayoutManager(context).apply {
                flexWrap = com.google.android.flexbox.FlexWrap.WRAP
                flexDirection = com.google.android.flexbox.FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = filterBottomSheetAdpaterEmotion
            }
            filterBottomSheetAdpaterEmotion.submitList(
                EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) }
            )
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

}