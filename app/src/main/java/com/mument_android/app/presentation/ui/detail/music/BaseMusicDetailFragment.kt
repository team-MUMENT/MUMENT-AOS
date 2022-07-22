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
import com.mument_android.app.presentation.ui.detail.mument.MumentClickListener
import com.mument_android.app.presentation.ui.detail.mument.MumentTagListAdapter
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_VERTICAL
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentBaseMusicDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseMusicDetailFragment(): Fragment() {
    protected var binding by AutoClearedValue<FragmentBaseMusicDetailBinding>()
    protected val musicDetailViewModel: MusicDetailViewModel by viewModels()

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
    }

    private fun changeMumentSort() {
        binding.run {
            tvSortLikeCount.setOnClickListener { musicDetailViewModel?.changeSelectedSort(tvSortLikeCount.text.toString()) }
            tvSortLatest.setOnClickListener { musicDetailViewModel?.changeSelectedSort(tvSortLatest.text.toString()) }
            musicDetailViewModel?.selectedSort?.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
                tvSortLatest.changeSelectedSortTheme(it)
                tvSortLikeCount.changeSelectedSortTheme(it)
            }
        }
    }

    protected fun setEveryMumentListAdapter(mumentListAdapter: MusicDetailMumentListAdapter) {
        binding.rvEveryMuments.run {
            addItemDecoration(RecyclerviewItemDivider(0, 15.dpToPx(requireContext()), IS_VERTICAL))
            adapter = mumentListAdapter

        }
    }

    private fun updateEveryMuments() {
        musicDetailViewModel.mumentList.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
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
        musicDetailViewModel.myMument.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
            it?.let {
                (binding.layoutMyMument.rvMumentTags.adapter as MumentTagListAdapter).submitList(mapTagList(it))
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