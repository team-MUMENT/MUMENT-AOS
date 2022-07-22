package com.mument_android.app.presentation.ui.record

import android.annotation.SuppressLint
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
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IMPRESSIVE
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.presentation.ui.customview.MumentDialog
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
    private val recordViewModel: RecordViewModel by activityViewModels()
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


        arguments?.getString(MUMENT_ID_FOR_EDIT)?.let {
            recordViewModel.mumentId.value = it
        }

        arguments?.getParcelable<MumentDetailEntity>(MUMENT_DETAIL_ENTITY)?.let {
            Timber.e("$it")
            recordViewModel.mumentData.value = it
            if (it.isFirst.tagIdx == 1) {
                recordViewModel.changeIsFirst(true)
            } else {
                recordViewModel.findIsFirst()
            }
        }

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

        modifyRevoke()
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
                    recordViewModel.addCheckedList(tag)
                    rvImpressionTagsAdapter.selectedTags.add(tag)
                }

                override fun removeCheckedTag(tag: TagEntity) {
                    recordViewModel.removeCheckedList(tag)
                    rvImpressionTagsAdapter.selectedTags.remove(tag)
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
                ImpressiveTag.values().map { TagEntity(TAG_IMPRESSIVE, it.tag, it.tagIndex) }
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
            binding.btnRecordFinish.isEnabled = recordViewModel.isSelectedMusic.value == true
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
                if (recordViewModel.mumentData.value != null) {
                    if (recordViewModel.mumentData.value!!.isFirst.tagIdx == 0) {
                        if (it) {
                            binding.btnRecordFirst.isChangeButtonFont(it)
                            binding.btnRecordSecond.isChangeButtonFont(!it)
                        } else {
                            binding.btnRecordFirst.isChangeButtonFont(it)
                            binding.btnRecordSecond.isChangeButtonFont(!it)
                            binding.btnRecordFirst.isClickable = false
                        }
                    } else {
                        binding.btnRecordFirst.isChangeButtonFont(!it)
                        binding.btnRecordSecond.isChangeButtonFont(it)
                    }
                } else {
                    if (!it) {
                        binding.btnRecordFirst.isChangeButtonFont(it)
                        binding.btnRecordSecond.isChangeButtonFont(!it)
                        binding.btnRecordFirst.isClickable = false
                    } else {
                        binding.btnRecordFirst.isChangeButtonFont(!it)
                        binding.btnRecordSecond.isChangeButtonFont(it)
                    }
                }
            } else {
                binding.btnRecordFirst.isChangeButtonFont(false)
                binding.btnRecordSecond.isChangeButtonFont(false)
            }
        }

        recordViewModel.mumentData.observe(viewLifecycleOwner) {
            if (it != null) {
                Timber.e("${it.impressionTags}")
                recordViewModel.selectedMusic.value = RecentSearchData(
                    it.musicInfo.id,
                    it.musicInfo.artist,
                    it.musicInfo.thumbnail,
                    it.musicInfo.name,
                    null
                )
                recordViewModel.setCheckTaglist(it.impressionTags ?: listOf())
                recordViewModel.setCheckTaglist(it.emotionalTags ?: listOf())
                recordViewModel.findIsFirst()
                recordViewModel.mumentContent.value = it.content
                binding.switchRecordSecret.isChecked = false

                recordViewModel.checkedTagList.value?.let { data ->
                    rvImpressionTagsAdapter.selectedTags.addAll(data)
                    rvEmotionalTagsAdapter.selectedTags.addAll(data)
                }
                Timber.d("HI $it")
            } else {

            }
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
            } else if (it.length <= 999) {
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
        binding.svRecord.scrollTo(0, 0)
        binding.etRecordWrite.text.clear()
        binding.tvRecordSecret.setText(R.string.record_open)
        binding.switchRecordSecret.isChecked = false
        binding.btnRecordFinish.isEnabled = false
        recordViewModel.mumentData.value = null

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
            if (recordViewModel.mumentId.value == "") {
                requireContext().snackBar(
                    binding.clRecordRoot,
                    getString(R.string.record_finish_record)
                )
                recordViewModel.mumentId.value = ""
                recordViewModel.postMument()

            } else {
                requireContext().snackBar(
                    binding.clRecordRoot,
                    getString(R.string.modify_record)
                )
                recordViewModel.mumentId.value = ""
                recordViewModel.putMument()
            }
        }
    }

    private fun modifyRevoke() {
        binding.btnModifyDelete.setOnClickListener {
            MumentDialogBuilder()
                .setHeader(getString(R.string.modify_header))
                .setBody(getString(R.string.modify_body))
                .setOption(true)
                .setAllowListener {
                    //곡 상세보기로 이동
                }
                .setCancelListener {
                    //그대로
                }
                .build()
                .show(childFragmentManager, this.tag)
        }
    }




    //곡에서 x버튼 클릭 리스너
    private fun isClickDelete() {
        binding.ivDelete.setOnClickListener {
            recordViewModel.removeSelectedMusic()
            binding.btnRecordFirst.isClickable = true
            binding.btnRecordFinish.isEnabled = false
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

    override fun onStop() {
        super.onStop()
        Timber.d("OnDestroy View")
        recordViewModel.mumentId.value = ""
        resetRecord()
        resetRecordTags()


    }


    companion object {
        const val MUMENT_ID_FOR_EDIT = "MUMENT_ID_FOR_EDIT"
        const val MUMENT_DETAIL_ENTITY = "MUMENT_DETAIL_ENTITY"
    }
}


