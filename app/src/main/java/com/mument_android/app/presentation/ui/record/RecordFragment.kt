package com.mument_android.app.presentation.ui.record

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.*
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.presentation.ui.record.viewmodel.RecordViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.databinding.FragmentRecordBinding
import timber.log.Timber


class RecordFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentRecordBinding>()
    private val recordViewModel: RecordViewModel by viewModels()
    private lateinit var recordTagAdapter: RecordTagAdapter
    private lateinit var recordTagAdapter2: RecordTagAdapter

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
        binding.etRecordWrite.movementMethod = ScrollingMovementMethod()
        recordTagAdapter = RecordTagAdapter(requireContext(),false,
            checkListener = {
                recordViewModel.addCheckedList(it)
            },
            unCheckListener = {
                recordViewModel.removeCheckedList(it)
            }
        )

        recordTagAdapter2 = RecordTagAdapter(requireContext(), true,
            checkListener = {
                recordViewModel.addCheckedList(it)
            },
            unCheckListener = {
                recordViewModel.removeCheckedList(it)
            })

        recordViewModel.checkedTagList.observe(viewLifecycleOwner) {
            Timber.e("$it")
        }

        with(binding.rvRecordImpressiveTags) {
            addItemDecoration( RecyclerviewItemDivider( 10.dpToPx(requireContext()), 10.dpToPx(requireContext())))
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = recordTagAdapter
            }
            recordTagAdapter.submitList(
                ImpressiveTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) })
        }

        with(binding.rvRecordEmotionalTags) {
            addItemDecoration(
                RecyclerviewItemDivider(
                    10.dpToPx(requireContext()),
                    10.dpToPx(requireContext())
                )
            )
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = recordTagAdapter2
            }
            recordTagAdapter2.submitList(
                EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) })
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

        with(binding) {
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
        typeface = ResourcesCompat.getFont(
            context, if (selected) R.font.notosans_bold else R.font.notosans_medium
        )
    }

    private fun TextView.isChangeRedLine() {
        typeface = ResourcesCompat.getFont(
            context, R.font.notosans_bold
        )
        setTextColor(Color.RED)
    }

    private fun TextView.isChangeBlack() {
        typeface = ResourcesCompat.getFont(
            context, R.font.notosans_medium
        )
        setTextColor(Color.GRAY)
    }

    private fun countText() {
        recordViewModel.text.observe(viewLifecycleOwner) {
            if (it.length >= 1000) {
                binding.tvRecordTextNum.isChangeRedLine()
            } else if (it.length == 999) {
                binding.tvRecordTextNum.isChangeBlack()
            }
        }
        recordViewModel.checkedTagList.observe(viewLifecycleOwner) {

            Timber.d(it.toString())
        }
    }
}