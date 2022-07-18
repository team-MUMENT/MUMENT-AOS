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
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
import com.mument_android.app.domain.entity.SearchResultData
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.presentation.ui.customview.MumentDialogBuilder
import com.mument_android.app.presentation.ui.home.BottomSheetSearchFragment
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.presentation.ui.record.viewmodel.RecordViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_GRIDLAYOUT
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.ViewUtils.snackBar
import com.mument_android.databinding.FragmentRecordBinding
import timber.log.Timber
import java.lang.reflect.Array.get


class RecordFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentRecordBinding>()
    private val recordViewModel: RecordViewModel by viewModels()
    private lateinit var rvImpressionTagsAdapter: RecordTagAdapter
    private lateinit var rvEmotionalTagsAdapter: RecordTagAdapter
    private val searchViewModel : SearchViewModel by viewModels()
    private  lateinit var searchListAdapter: SearchListAdapter

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
        setTagRecyclerView()
        countText()
        resetRvImpressionTags()
        firstListenClickEvent()
        secondListenClickEvent()
        switchClickEvent()
        scrollEditTextView()
        initBottomSheet()
        getAllData()
    }

    private fun setTagRecyclerView() {

        rvImpressionTagsAdapter = RecordTagAdapter(
            requireContext(),
            false,
            object : RecordTagAdapter.RecordTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    recordViewModel.addCheckedList(tag)
                    rvEmotionalTagsAdapter.selectedTags.add(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    recordViewModel.removeCheckedList(tag)
                    rvEmotionalTagsAdapter.selectedTags.remove(tag)
                }

                override fun alertMaxCount() {
                    requireContext().snackBar(
                        binding.clRecordRoot,
                        requireContext().getString(R.string.record_snackbar_tag_info)
                    )
                }
            }

        )

        rvEmotionalTagsAdapter = RecordTagAdapter(
            requireContext(),
            false,
            object : RecordTagAdapter.RecordTagCheckListener {
                override fun addCheckedTag(tag: TagEntity) {
                    rvImpressionTagsAdapter.selectedTags.add(tag)
                    recordViewModel.addCheckedList(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    rvImpressionTagsAdapter.selectedTags.remove(tag)
                    recordViewModel.removeCheckedList(tag)
                }

                override fun alertMaxCount() {
                    requireContext().snackBar(
                        binding.clRecordRoot,
                        requireContext().getString(R.string.record_snackbar_tag_info)
                    )
                }
            }
        )

        with(binding.rvRecordImpressiveTags) {
            setItemDecoration(this)
            setImpressiveRvFlexBoxLayout()
        }

        with(binding.rvRecordEmotionalTags) {
            setItemDecoration(this)
            setEmotionalRvFlexBoxLayout()

        }
    }

    private fun setItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(
            RecyclerviewItemDivider(
                10.dpToPx(requireContext()),
                10.dpToPx(requireContext()),
                IS_GRIDLAYOUT
            )
        )
    }

    private fun setImpressiveRvFlexBoxLayout() {
        with(binding.rvRecordImpressiveTags) {
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = rvImpressionTagsAdapter
            }
            rvImpressionTagsAdapter.submitList(
                ImpressiveTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) }
            )
        }
    }

    private fun setEmotionalRvFlexBoxLayout() {
        with(binding.rvRecordEmotionalTags) {
            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }.let {
                layoutManager = it
                adapter = rvEmotionalTagsAdapter
            }
            rvEmotionalTagsAdapter.submitList(
                EmotionalTag.values().map { TagEntity(TAG_EMOTIONAL, it.tag, it.tagIndex) }
            )
        }
    }

    private fun resetRvImpressionTags() {
        binding.btnRecordReset.setOnClickListener {
            resetButtonClickEvent()
        }
    }

    private fun RecyclerView.resetCheckedTags(adapter: RecordTagAdapter) {
        (0 until adapter.itemCount).forEach {
            getChildViewHolder(get(it)).run {
                (this as RecordTagAdapter.RecordTagViewHolder).binding.cbTag.isChecked = false
            }
        }
    }

    private fun firstListenClickEvent() {
        binding.btnRecordFirst.isChangeButtonFont(true)
        with(binding) {
            btnRecordFirst.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(true)
                btnRecordSecond.isChangeButtonFont(false)
                recordViewModel!!.checkIsFirst(true)
            }
        }
    }

    private fun secondListenClickEvent() {
        with(binding) {
            binding.btnRecordSecond.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(false)
                btnRecordSecond.isChangeButtonFont(true)
                recordViewModel!!.checkIsFirst(false)
            }
        }
    }

    private fun switchClickEvent() {

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
        typeface = ResourcesCompat.getFont(context, R.font.notosans_bold)
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
    }

    private fun scrollEditTextView() {
        binding.etRecordWrite.movementMethod = ScrollingMovementMethod()
    }

    private fun resetButtonClickEvent() {

        MumentDialogBuilder()
            .setHeader(getString(R.string.record_reset_header))
            .setBody(getString(R.string.record_reset_body))
            .setAllowListener {
                resetRecordTags()
            }
            .setCancelListener {}
            .build()
            .show(childFragmentManager, this.tag)
    }

    private fun resetRecordTags() {
        binding.rvRecordImpressiveTags.resetCheckedTags(rvImpressionTagsAdapter)
        binding.rvRecordEmotionalTags.resetCheckedTags(rvEmotionalTagsAdapter)
        rvEmotionalTagsAdapter.selectedTags.clear()
        rvImpressionTagsAdapter.selectedTags.clear()
        recordViewModel.resetCheckedList()
    }

    private fun initBottomSheet() {
        binding.btnRecordSearch.setOnClickListener {
            BottomSheetSearchFragment.newInstance {
                recordViewModel.changeSelectedMusic(it)
            }.show(parentFragmentManager, "Hi")
            recordViewModel.checkSelectedMusic(true)

        }

        recordViewModel.selectedMusic.observe(viewLifecycleOwner) {
            Timber.e("$it")
            recordViewModel.checkSelectedMusic(true)
        }
    }

    private fun getAllData() {
        binding.btnRecordFinish.setOnClickListener {
            Timber.e(
                "버튼 : ${recordViewModel.isFirst.value}\n 태그 : ${rvImpressionTagsAdapter.selectedTags.map { 
                    it.tagIdx
                }}\n 감상기록 : ${recordViewModel.text.value}\n 공개글 : ${binding.switchRecordSecret.isChecked}"
            )
        }
    }

    private fun isSelectedMusic(){

    }

}


