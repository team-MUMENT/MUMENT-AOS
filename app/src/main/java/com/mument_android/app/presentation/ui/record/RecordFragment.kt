package com.mument_android.app.presentation.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.*
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.presentation.ui.record.viewmodel.RecordViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentRecordBinding
import timber.log.Timber


class RecordFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentRecordBinding>()
    private val recordViewModel: RecordViewModel by viewModels()
    private var recordTagAdapter = RecordTagAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRecordBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recordViewModel = recordViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setEmotionalList()
        clickEvent()
        countText()
    }

    private fun setEmotionalList() {
        with(binding.rvRecordEmotionalTags) {
            adapter = RecordTagAdapter()
            FlexboxLayoutManager(context).apply{
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW


            }.let {
                binding.rvRecordEmotionalTags.layoutManager =it
                binding.rvRecordEmotionalTags.adapter = recordTagAdapter
            }

            (adapter as RecordTagAdapter).submitList(EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex ) })

//            EmotionalTag.values().map { requireContext().getString(it.tag) }.forEach {
//                Timber.e("tag: $it")
//            }
        }

        with(binding.rvRecordEmotionalTags2) {
            adapter = RecordTagAdapter()

            FlexboxLayoutManager(context).apply{
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW

            }.let {
                binding.rvRecordEmotionalTags2.layoutManager =it
                binding.rvRecordEmotionalTags2.adapter = recordTagAdapter
            }

            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection
            }.also { binding.rvRecordEmotionalTags2.layoutManager = it }

            val itemDecoration = FlexboxItemDecoration(context).apply {
                setDrawable(ContextCompat.getDrawable(context, R.drawable.rectangle_fill_blue3_20dp))
                setOrientation(FlexboxItemDecoration.HORIZONTAL)
            }
            if (binding.rvRecordEmotionalTags2.itemDecorationCount == 0) {
                binding.rvRecordEmotionalTags2.addItemDecoration(itemDecoration)
            }
            (adapter as RecordTagAdapter).submitList(EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex ) })

//            EmotionalTag.values().map { requireContext().getString(it.tag) }.forEach {
//                Timber.e("tag: $it")
//            }
        }

    }

    private fun clickEvent() {
        binding.btnRecordFirst.isChangeButtonFont(true)
        with(binding) {
            btnRecordFirst.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(true)
                btnRecordSecond.isChangeButtonFont(false)
                recordViewModel!!.checkIsFirst(true)
            }
        }

        with(binding){
            binding.btnRecordSecond.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(false)
                btnRecordSecond.isChangeButtonFont(true)
                recordViewModel!!.checkIsFirst(false)
            }
        }

        binding.switchRecordSecret.setOnClickListener {
            if (binding.switchRecordSecret.isChecked) {
                binding.tvRecordSecret.setText(R.string.record_secret)
            } else {
                binding.tvRecordSecret.setText(R.string.record_open)
            }
        }
    }

    private fun Button.isChangeButtonFont(selected: Boolean) {
        isSelected = selected
        typeface = ResourcesCompat.getFont(context, if (selected) R.font.notosans_bold else R.font.notosans_medium
        )
    }

    private fun countText() {
        recordViewModel.text.observe(viewLifecycleOwner) {
            Timber.d("${it}")
        }
    }
}