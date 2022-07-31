package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.detail.MumentSummaryEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.presentation.ui.detail.mument.MumentTagListAdapter
import com.mument_android.app.presentation.ui.detail.mument.navigator.MoveRecordProvider
import com.mument_android.app.util.*
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_VERTICAL
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.databinding.FragmentBaseMusicDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseMusicDetailFragment(): Fragment() {
    protected var binding by AutoClearedValue<FragmentBaseMusicDetailBinding>()
    protected val musicDetailViewModel: MusicDetailViewModel by viewModels()
    @Inject lateinit var recordProvider: MoveRecordProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBaseMusicDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicDetailViewModel = musicDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        changeMumentSort()
        updateEveryMuments()
        setMyMumentTagList()
        binding.ivBack.setOnClickListener { findNavController().popBackStack() }

        binding.tvRecordMument.click {
            musicDetailViewModel.musicInfo.value?.let { musicInfo ->
                recordProvider.recordMusic(Music(musicInfo.id, musicInfo.name, musicInfo.artist, musicInfo.thumbnail))
            }
        }
    }

    private fun changeMumentSort() {
        binding.tvSortLikeCount.click { musicDetailViewModel.changeSelectedSort(binding.tvSortLikeCount.text.toString()) }
        binding.tvSortLatest.click { musicDetailViewModel.changeSelectedSort(binding.tvSortLatest.text.toString()) }
        collectFlowWhenStarted(musicDetailViewModel.selectedSort) {
            binding.tvSortLatest.changeSelectedSortTheme(it)
            binding.tvSortLikeCount.changeSelectedSortTheme(it)
        }
    }

    protected fun setEveryMumentListAdapter(mumentListAdapter: MusicDetailMumentListAdapter) {
        binding.rvEveryMuments.run {
            addItemDecoration(RecyclerviewItemDivider(0, 15.dpToPx(requireContext()), IS_VERTICAL))
            adapter = mumentListAdapter
        }
    }

    private fun updateEveryMuments() {
        collectFlowWhenStarted(musicDetailViewModel.mumentList) {
            (binding.rvEveryMuments.adapter as MusicDetailMumentListAdapter).submitList(it)
        }
    }

    private fun AppCompatTextView.changeSelectedSortTheme(selectedSort: String) {
        isSelected = selectedSort == text.toString()
    }

    private fun setMyMumentTagList() {
        binding.layoutMyMument.rvMumentTags.run {
            adapter = MumentTagListAdapter()
        }
        collectFlowWhenStarted(musicDetailViewModel.myMument) { mumentSummary ->
            mumentSummary?.let {
                (binding.layoutMyMument.rvMumentTags.adapter as MumentTagListAdapter).submitList(mapTagList(mumentSummary))
            }
        }
    }

    private fun mapTagList(mument: MumentSummaryEntity): List<TagEntity> {
        val cardTags = mutableListOf<TagEntity>()
        val isFirst = if (mument.isFirst) R.string.tag_is_first else R.string.tag_has_heard
        cardTags.add( TagEntity(TagEntity.TAG_IS_FIRST, isFirst,  if (mument.isFirst) 1 else 0) )
        cardTags.addAll(
            mument.cardTag.map { tagIdx ->
                val type = if (tagIdx < 200) TagEntity.TAG_IMPRESSIVE else TagEntity.TAG_EMOTIONAL
                val tag = if (tagIdx < 200) ImpressiveTag.findImpressiveStringTag(tagIdx) else EmotionalTag.findEmotionalStringTag(tagIdx)
                TagEntity(type, tag, tagIdx)
            }
        )
        return cardTags
    }
}