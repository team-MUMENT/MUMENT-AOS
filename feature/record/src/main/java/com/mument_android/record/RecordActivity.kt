package com.mument_android.record

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.angdroid.navigation.MoveMusicDetailNavigatorProvider
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mument_android.core.model.TagEntity
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.TO_MUSIC_DETAIL
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.core_dependent.util.RecyclerviewItemDivider
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.core_dependent.util.ViewUtils.snackBar
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.record.MumentModifyEntity
import com.mument_android.record.databinding.ActivityRecordBinding
import com.mument_android.record.viewmodels.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecordActivity :
    BaseActivity<ActivityRecordBinding>(inflate = ActivityRecordBinding::inflate) {
    private val recordViewModel: RecordViewModel by viewModels()
    private lateinit var rvImpressionTagsAdapter: RecordTagAdapter
    private lateinit var rvEmotionalTagsAdapter: RecordTagAdapter

    @Inject
    lateinit var moveMusicDetailNavigatorProvider: MoveMusicDetailNavigatorProvider

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recordViewModel = recordViewModel

        val mumentModifyEntity = intent.getParcelableExtra<MumentModifyEntity>("MumentModifyEntity")
        val recentSearchData = intent.getParcelableExtra<RecentSearchData>("RecentSearchData")
        val mumentId = intent.getStringExtra("MumentID")
        mumentModifyEntity?.let { mument ->
            recentSearchData?.let { music ->
                mumentId?.let { mumentId ->
                    recordViewModel.changeSelectedMusic(music)
                    recordViewModel.setIntentData(
                        mumentModifyEntity, muId = mumentId
                    )
                }
            }
        }

        setTagRecyclerView()
        countText()

        firstListenClickEvent()
        secondListenClickEvent()

        switchClickEvent()

        scrollEditTextView()
        initBottomSheet()
        getAllData()
        isClickDelete()
        observingListen()

        deleteBtnEvent()
    }


    //태그들 추가, 삭제 -> 5개 판별
    private fun setTagRecyclerView() {
        rvImpressionTagsAdapter = RecordTagAdapter(
            this,
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
                    this@RecordActivity.snackBar(
                        binding.clRecordRoot,
                        this@RecordActivity.getString(R.string.record_snackbar_tag_info)
                    )
                }
            }
        )

        rvEmotionalTagsAdapter = RecordTagAdapter(
            this,
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
                    this@RecordActivity.snackBar(
                        binding.clRecordRoot,
                        this@RecordActivity.getString(R.string.record_snackbar_tag_info)
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
                10.dpToPx(this),
                10.dpToPx(this),
                RecyclerviewItemDivider.IS_GRIDLAYOUT
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
                ImpressiveTag.values()
                    .map { TagEntity(TagEntity.TAG_IMPRESSIVE, it.tag, it.tagIndex) }
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
                EmotionalTag.values()
                    .map { TagEntity(TagEntity.TAG_EMOTIONAL, it.tag, it.tagIndex) }
            )
        }
    }

    //처음들었어요 버튼 눌렀을 때
    @SuppressLint("ClickableViewAccessibility")
    private fun firstListenClickEvent() {
        with(binding) {
            btnRecordFirst.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(true)
                btnRecordSecond.isChangeButtonFont(false)
                recordViewModel?.isFirstDuplicate = true
            }
            btnRecordFirst.setOnTouchListener { view, _ ->
                if (!binding.btnRecordFirst.isClickable) {
                    this@RecordActivity.snackBar(
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
                //Log.e("isFirstTagIndex" , "${recordViewModel.mumentData.value!!.mument.isFirst.tagIdx}")
            }.show(supportFragmentManager, "bottom sheet")
        }
        recordViewModel.selectedMusic.observe(this) {
            recordViewModel.checkSelectedMusic(it != null)
            binding.tvRecordFinish.isEnabled = recordViewModel.isSelectedMusic.value == true
            binding.tvRecordFinish.isSelected = (recordViewModel.isSelectedMusic.value == true)
        }
    }

    //처음 들었어요 다시들었어요 처리
    private fun observingListen() {
        recordViewModel.isFirst.observe(this) {
            Log.e("IS FIRST", it.toString())
            recordViewModel.isFirstDuplicate = it ?: true
            if (it != null) {
                //기록하기 일 때, 기록한 적이 없다면
                if (recordViewModel.modifyMumentId.value == null) {
                    //처음들었다면
                    if (it) {
                        //처음들었어요 true, 다시들었어요 false
                        binding.btnRecordFirst.isChangeButtonFont(it)
                        binding.btnRecordSecond.isChangeButtonFont(false)
                    }
                    //다시 들었다면 -> isFirst.value == false
                    else {
                        //처음들었어요 false, 다시들었어요 false , 처음들었어요 선택 못하도록
                        binding.btnRecordFirst.isChangeButtonFont(it)
                        binding.btnRecordSecond.isChangeButtonFont(true)
                        binding.btnRecordFirst.isClickable = false
                    }
                }
                //수정하기 일 때
                else {
                    //다시들었어요 일 때
                    if (!it) {  //isFirst 가 false 일 때"
                        Log.e("수정하기에서 다시 들었어요", "${recordViewModel.isFirst.value}")
                        binding.btnRecordFirst.isChangeButtonFont(it)
                        binding.btnRecordSecond.isChangeButtonFont(true)
                        binding.btnRecordFirst.isClickable = false
                    } else {
                        Log.e("수정하기에서 처음 들었어요", "${recordViewModel.isFirst.value}")
                        binding.btnRecordFirst.isChangeButtonFont(it)
                        binding.btnRecordSecond.isChangeButtonFont(!it)
                    }
                }
            } else {
                //맨처음 들어왔고 곡 선택 안되어있을 때
                binding.btnRecordFirst.isChangeButtonFont(false)
                binding.btnRecordSecond.isChangeButtonFont(false)
            }
        }

        recordViewModel.mumentData.observe(this) {
            if (it == null) {
/*                recordViewModel.selectedMusic.value = RecentSearchData(
                    it.musicInfo.id,
                    it.mument.musicInfo.artist,
                    it.mument.musicInfo.thumbnail,
                    it.mument.musicInfo.name,
                    null
                )*/
                recordViewModel.setCheckTaglist(recordViewModel.checkedTagList.value ?: listOf())
//                recordViewModel.findIsFirst()
//                recordViewModel.mumentContent.value = it.mument.content
                binding.switchRecordSecret.isChecked = false

                recordViewModel.checkedTagList.value?.let { data ->
                    rvImpressionTagsAdapter.selectedTags.addAll(data)
                    rvEmotionalTagsAdapter.selectedTags.addAll(data)
                }
            } else {

            }
        }
    }

    private fun secondListenClickEvent() {
        with(binding) {
            binding.btnRecordSecond.setOnClickListener {
                btnRecordFirst.isChangeButtonFont(false)
                btnRecordSecond.isChangeButtonFont(true)
                recordViewModel?.isFirstDuplicate = false
            }
        }
    }

    // length에 대한 조절
    private fun countText() {
        recordViewModel.mumentContent.observe(this) {
            if (it.length >= 1000) {
                binding.tvRecordTextNum.isChangeRedLine()
            } else {
                binding.tvRecordTextNum.isChangeBlack()
            }
        }
        recordViewModel.createdMumentId.observe(this) {
            if (it != null) {
                if (recordViewModel.mumentData.value != null) {
                    onBackPressed()
                    recordViewModel.mumentData.value = null
                } else {
                    recordViewModel.selectedMusic.value?.let { it1 ->
                        moveMusicDetailNavigatorProvider.musicMument(
                            it1._id
                        )
                    }
                }
            }
        }
        recordViewModel.modifyMumentId.observe(this) {

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

    //완료버튼 눌렀을 때
    private fun getAllData() {
        binding.tvRecordFinish.setOnClickListener {
            if (recordViewModel.mumentId.value == "") {
                this.snackBar(
                    binding.clRecordRoot,
                    getString(R.string.record_finish_record)
                )
                recordViewModel.mumentId.value = ""
                recordViewModel.postMument()
            } else {
                this.snackBar(
                    binding.clRecordRoot,
                    getString(R.string.modify_record)
                )
                recordViewModel.modifyMument()
                recordViewModel.mumentId.value = ""
            }

            collectFlowWhenStarted(recordViewModel.isCreateSuccessful) { isSuccessful ->
                if (isSuccessful) {
                    Log.e("success", "success")
                    val mumentId = recordViewModel.createdMumentId.value ?: ""
                    recordViewModel.selectedMusic.value?.let { music ->
                        Intent().run {
                            putExtra(TO_MUSIC_DETAIL, TO_MUSIC_DETAIL)
                            putExtra(MUMENT_ID, mumentId)
                            putExtra(MUSIC_INFO_ENTITY, music.toMusicInfo())
                            setResult(RESULT_OK, this)
                            finish()
                        }
                    }
                } else {
                    showToast("뮤멘트 기록하기 실패")
                }
            }

            collectFlowWhenStarted(recordViewModel.isModifySuccessful) { isSuccessful ->
                if (isSuccessful) {
                    Log.e("success", "success")
                    val mumentId = recordViewModel.modifyMumentId.value ?: ""
                    recordViewModel.selectedMusic.value?.toMusicInfo()?.let { music ->
                        Intent().run {
                            putExtra(MUMENT_ID, mumentId)
                            putExtra(MUSIC_INFO_ENTITY, music)
                            setResult(RESULT_OK, this)
                            finish()
                        }
                    }
                } else {

                }
            }
        }
    }

    //x 버튼 눌렀을 때 (기록하기/수정하기)
    private fun deleteBtnEvent() {
        binding.btnRecordDelete.setOnClickListener {
            if (recordViewModel.mumentId.value?.isEmpty() == true) {
                MumentDialogBuilder()
                    .setHeader(getString(R.string.record_delete_header))
                    .setBody(getString(R.string.record_delete_body))
                    .setAllowListener("확인") {
                        onBackPressed()
                    }
                    .setCancelListener {}
                    .build()
                    .show(supportFragmentManager, attributionTag)

            } else {
                MumentDialogBuilder()
                    .setHeader(getString(R.string.modify_header))
                    .setBody(getString(R.string.modify_body))
                    .setAllowListener("확인") {
                        //곡 상세보기로 이동
                    }
                    .setCancelListener {
                        //그대로
                    }
                    .build()
                    .show(supportFragmentManager, attributionTag)

            }
        }
    }

    //곡에서 x버튼 클릭 리스너
    private fun isClickDelete() {
        binding.ivDelete.setOnClickListener {
            recordViewModel.removeSelectedMusic()
            binding.btnRecordFirst.isClickable = true
            binding.tvRecordFinish.isEnabled = false
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
        recordViewModel.mumentId.value = ""
    }

    companion object {
        const val MUMENT_ID_FOR_EDIT = "MUMENT_ID_FOR_EDIT"
        const val MUMENT_DETAIL_ENTITY = "MUMENT_DETAIL_ENTITY"
    }

}