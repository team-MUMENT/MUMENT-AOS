package com.mument_android.app.presentation.ui.locker

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.presentation.ui.locker.adapter.FilterBottomSheetAdapter
import com.mument_android.app.presentation.ui.record.RecordTagAdapter
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.databinding.FragmentLockerFilterBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LockerFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding by AutoClearedValue<FragmentLockerFilterBottomSheetBinding>()
    private var recordTagAdapter = FilterBottomSheetAdapter()
    private var emotionTagAdapter = FilterBottomSheetAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLockerFilterBottomSheetBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEmotionalList()
        setImpressList()
    }

    private fun setEmotionalList() {
        with(binding.rvImpressive) {
            adapter = FilterBottomSheetAdapter()
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW


            }.let {
                layoutManager = it
                adapter = emotionTagAdapter
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
    }

    private fun setImpressList() {
        with(binding.rvImpress) {
            adapter = FilterBottomSheetAdapter()
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW


            }.let {
                binding.rvImpress.layoutManager = it
                binding.rvImpress.adapter = recordTagAdapter
            }

            (adapter as FilterBottomSheetAdapter).submitList(
                EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) })
            binding.rvImpress.addItemDecoration(
                RecyclerviewItemDivider(
                    7.dpToPx(requireContext()),
                    5.dpToPx(requireContext())
                )
            )

            val emotionalTags =
                EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) }
            val impressionTags =
                ImpressiveTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) }
            val emotions = emotionalTags.toMutableList().addAll(impressionTags)


        }
    }
}