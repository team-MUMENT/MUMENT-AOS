package com.mument_android.app.presentation.ui.locker

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IMPRESSIVE
import com.mument_android.app.presentation.ui.customview.MumentTagCheckBox
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetAdapter
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetSelectedAdapter
import com.mument_android.app.presentation.ui.locker.viewmodel.LockerViewModel
import com.mument_android.app.presentation.ui.record.viewmodel.RecordViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.ViewUtils.dpToPx
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
        binding.clFilterBottomSheet.setBackgroundResource(R.drawable.rectangle_fill_white_top_11dp)


        binding.clFilterBottomSheet.layoutParams.height =
            resources.displayMetrics.heightPixels * 93 / 100
        binding.executePendingBindings()

        setEmotionalList()
        resetClickListener()
        setSelectedTag()
        closeBtnListener()

    }



    private fun setEmotionalList() {
        with(binding.rvImpressive) {
            filterBottomSheetAdapterImpress = FilterBottomSheetAdapter(requireContext(),
                checkListener = {
                    lockerViewModel.addCheckedList(it)
                },
                unCheckListener = {
                    lockerViewModel.removeCheckedList(it)
                })
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW

            }.let {
                layoutManager = it
                adapter = filterBottomSheetAdapterImpress
            }

            (adapter as FilterBottomSheetAdapter).submitList(
                ImpressiveTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) })
            binding.rvImpressive.addItemDecoration(
                RecyclerviewItemDivider(
                    7.dpToPx(requireContext()),
                    5.dpToPx(requireContext())
                )
            )

        }
        with(binding.rvEmotion) {
            filterBottomSheetAdpaterEmotion = FilterBottomSheetAdapter(requireContext(),
                checkListener = {
                    lockerViewModel.addCheckedList(it)
                },
                unCheckListener = {
                    lockerViewModel.removeCheckedList(it)
                })
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW


            }.let {
                binding.rvEmotion.layoutManager = it
                binding.rvEmotion.adapter = filterBottomSheetAdpaterEmotion
            }

            (adapter as FilterBottomSheetAdapter).submitList(
                EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) }
            )
            binding.rvEmotion.addItemDecoration(
                RecyclerviewItemDivider(
                    7.dpToPx(requireContext()),
                    5.dpToPx(requireContext())
                )
            )
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
                //val impressTags = ImpressiveTag.values().map { TagEntity(TAG_IMPRESSIVE, it.tag, it.tagIndex) }
                //impressTags.indexOf(tag).let { syncSelectedTags(it) }
                lockerViewModel.removeCheckedList(tag)
                syncSelectedTags(filterBottomSheetAdpaterEmotion.currentList.indexOf(tag))
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
            (viewHolder as FilterBottomSheetAdapter.BottomSheetFilterHolder).binding.cbTag.isChecked = false
        }

        /*
        binding.rvImpressive.let{ recyclerview ->
            val view = recyclerview[position]
            val viewHolder = recyclerview.getChildViewHolder(view)
            (viewHolder as FilterBottomSheetAdapter.BottomSheetFilterHolder).binding.cbTag.isChecked = false
        }
         */
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

}