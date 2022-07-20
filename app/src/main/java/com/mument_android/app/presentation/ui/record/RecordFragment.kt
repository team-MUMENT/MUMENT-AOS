package com.mument_android.app.presentation.ui.record

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.presentation.ui.customview.MumentDialogBuilder
import com.mument_android.app.presentation.ui.home.BottomSheetSearchFragment
import com.mument_android.app.presentation.ui.record.viewmodel.RecordViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_GRIDLAYOUT
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.ViewUtils.snackBar
import com.mument_android.databinding.FragmentRecordBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RecordFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentRecordBinding>()
    private val recordViewModel: RecordViewModel by viewModels()
    private lateinit var rvImpressionTagsAdapter: RecordTagAdapter
    private lateinit var rvEmotionalTagsAdapter: RecordTagAdapter

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
        isClickDelete()
        observingListen()
    }

    //태그들 추가, 삭제 -> 5개 판별
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

    // 태그들 마진값
    private fun setItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(
            RecyclerviewItemDivider(
                10.dpToPx(requireContext()),
                10.dpToPx(requireContext()),
                IS_GRIDLAYOUT
            )
        )
    }

    //flexBox 적용
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

    //첫번째(처음 들었어요) 버튼 클릭 리스너
    @SuppressLint("ClickableViewAccessibility")
    private fun firstListenClickEvent() {
        with(binding) {
            btnRecordFirst.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(true)
                btnRecordSecond.isChangeButtonFont(false)
            }
            btnRecordFirst.setOnTouchListener { view, _ ->
                if (!binding.btnRecordFirst.isClickable) {
                    requireContext().snackBar(
                        binding.clRecordRoot,
                        getString(R.string.record_snackbar_first_info)
                    )
                }
                false
            }
        }
    }

    //바텀시트 올라오면서 처리
    private fun initBottomSheet() {
        binding.btnRecordSearch.setOnClickListener {
            BottomSheetSearchFragment.newInstance {
                recordViewModel.changeSelectedMusic(it)
                recordViewModel.findIsFirst()
            }.show(parentFragmentManager, "bottom sheet")
            Timber.d(recordViewModel.selectedMusic.value.toString())
        }
        recordViewModel.selectedMusic.observe(viewLifecycleOwner) {
            Timber.e("Test Selected Music : $it")
            recordViewModel.checkSelectedMusic(it != null)
            binding.btnRecordFinish.isSelected = (recordViewModel.isSelectedMusic.value == true)
        }
    }

    //reset버튼 클릭 리스너
    private fun resetRvImpressionTags() {
        binding.btnRecordReset.setOnClickListener {
            resetButtonClickEvent()
        }
    }

    //처음 들었어요 다시들었어요 처리
    private fun observingListen() {
        recordViewModel.isFirst.observe(viewLifecycleOwner) {
            if (it != null) {
                if (!it) {
                    binding.btnRecordFirst.isChangeButtonFont(it)
                    binding.btnRecordSecond.isChangeButtonFont(!it)
                    binding.btnRecordFirst.isClickable = false
                } else {
                    binding.btnRecordFirst.isChangeButtonFont(!it)
                    binding.btnRecordSecond.isChangeButtonFont(it)
                }
            }else{
                binding.btnRecordFirst.isChangeButtonFont(false)
                binding.btnRecordSecond.isChangeButtonFont(false)
            }
            Timber.d("Observe $it")
        }
    }


    private fun secondListenClickEvent() {
        with(binding) {
            binding.btnRecordSecond.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(false)
                btnRecordSecond.isChangeButtonFont(true)
            }
        }
    }

    //비밀글 공개글 버튼 리스너
    private fun switchClickEvent() {
        binding.switchRecordSecret.setOnClickListener {
            if (binding.switchRecordSecret.isChecked) {
                binding.tvRecordSecret.setText(R.string.record_secret)
            } else {
                binding.tvRecordSecret.setText(R.string.record_open)
            }
        }
    }

    // length에 대한 조절
    private fun countText() {
        recordViewModel.mumentContent.observe(viewLifecycleOwner) {
            if (it.length >= 1000) {
                binding.tvRecordTextNum.isChangeRedLine()
            } else if (it.length == 999) {
                binding.tvRecordTextNum.isChangeBlack()
            }
        }
    }

    //editTex 스크롤 지정
    private fun scrollEditTextView() {
        binding.etRecordWrite.movementMethod = ScrollingMovementMethod()
        binding.etRecordWrite.setOnClickListener {
            binding.svRecord.scrollTo(0, binding.tvRecordWriteTitle.top)
        }

        binding.etRecordWrite.setOnFocusChangeListener { view, b ->
            binding.svRecord.scrollTo(0, binding.tvRecordWriteTitle.top)
        }
    }

    //리셋버튼 클릭 및 알럿
    private fun resetButtonClickEvent() {
        MumentDialogBuilder()
            .setHeader(getString(R.string.record_reset_header))
            .setBody(getString(R.string.record_reset_body))
            .setOption(true)
            .setAllowListener {
                resetRecord()
                resetRecordTags()
            }
            .setCancelListener {}
            .build()
            .show(childFragmentManager, this.tag)
    }

    //reset 처리
    private fun resetRecord() {
        binding.btnRecordFirst.isChangeButtonFont(false)
        binding.btnRecordSecond.isChangeButtonFont(false)
        binding.btnRecordFirst.isClickable = true
        recordViewModel.removeSelectedMusic()
        binding.clRecordRoot.scrollTo(0, 0)
        binding.etRecordWrite.text.clear()
        binding.tvRecordSecret.setText(R.string.record_open)
        binding.switchRecordSecret.isChecked = false
    }

    //tag 라셋
    private fun resetRecordTags() {
        binding.rvRecordImpressiveTags.resetCheckedTags(rvImpressionTagsAdapter)
        binding.rvRecordEmotionalTags.resetCheckedTags(rvEmotionalTagsAdapter)
        rvEmotionalTagsAdapter.selectedTags.clear()
        rvImpressionTagsAdapter.selectedTags.clear()
        recordViewModel.resetCheckedList()
    }

    //완료버튼 눌렀을 때
    private fun getAllData() {
        binding.btnRecordFinish.setOnClickListener {
            recordViewModel.postMument()
        }
    }

    //곡에서 x버튼 클릭 리스너
    private fun isClickDelete() {
        binding.ivDelete.setOnClickListener {
            recordViewModel.removeSelectedMusic()
            binding.btnRecordFirst.isClickable = true
            binding.btnRecordFirst.isChangeButtonFont(false)
            binding.btnRecordSecond.isChangeButtonFont(false)
        }
    }

    // Extension Function
    //버튼 폰트 지정
    private fun Button.isChangeButtonFont(selected: Boolean) {
        isSelected = selected
        typeface = ResourcesCompat.getFont(
            context, if (selected) R.font.notosans_bold else R.font.notosans_medium
        )
    }

    //1000자 넘어가면 색상 변경
    private fun TextView.isChangeRedLine() {
        typeface = ResourcesCompat.getFont(context, R.font.notosans_bold)
        setTextColor(Color.RED)
    }

    //안 넘어갔을 때 색상
    private fun TextView.isChangeBlack() {
        typeface = ResourcesCompat.getFont(
            context, R.font.notosans_medium
        )
        setTextColor(Color.GRAY)
    }

    //태그들 있는거 for문 돌면서 해지
    private fun RecyclerView.resetCheckedTags(adapter: RecordTagAdapter) {
        (0 until adapter.itemCount).forEach {
            getChildViewHolder(get(it)).run {
                (this as RecordTagAdapter.RecordTagViewHolder).binding.cbTag.isChecked = false
            }
        }
    }
}
