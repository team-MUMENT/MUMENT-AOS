package com.mument_android.app.presentation.ui.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag.Companion.findEmotionalStringTag
import com.mument_android.app.data.enumtype.ImpressiveTag.Companion.findImpressiveStringTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IMPRESSIVE
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.app.domain.entity.detail.MumentSummaryEntity
import com.mument_android.app.presentation.ui.detail.mument.MumentClickListener
import com.mument_android.app.presentation.ui.detail.mument.MumentTagListAdapter
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemMusicDetailListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicDetailMumentListAdapter(private val mumentClickListener: MumentClickListener): ListAdapter<MumentSummaryEntity, MusicDetailMumentListAdapter.MusicDetailMumentListViewHolder>(
    GlobalDiffCallBack<MumentSummaryEntity>()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicDetailMumentListViewHolder {
        val binding = ItemMusicDetailListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicDetailMumentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicDetailMumentListViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.mumentSummary, getItem(position))
            viewClickable.setOnClickListener { mumentClickListener.showMumentDetail(getItem(position).mumentId) }
            checkLikeMument(holder)
            setMumentTagList(holder)
        }
    }

    private fun checkLikeMument(holder: MusicDetailMumentListViewHolder) {
        val musicDetail = getItem(holder.absoluteAdapterPosition)
        holder.binding.run {
            cbHeart.setOnClickListener {
                val likeCount = musicDetail.likeCount
                if (cbHeart.isChecked) mumentClickListener.likeMument(musicDetail.mumentId) else mumentClickListener.cancelLikeMument(musicDetail.mumentId)
                val updatedLikeCount = when {
                    musicDetail.isLiked && cbHeart.isChecked -> likeCount
                    musicDetail.isLiked && !cbHeart.isChecked -> likeCount-1
                    !musicDetail.isLiked && cbHeart.isChecked -> likeCount+1
                    !musicDetail.isLiked && !cbHeart.isChecked -> likeCount
                    else -> likeCount
                }
                tvLikeCount.text = updatedLikeCount.toString()
            }
        }
    }

    private fun setMumentTagList(holder: MusicDetailMumentListViewHolder) {
        val mument = getItem(holder.absoluteAdapterPosition)
        CoroutineScope(Dispatchers.Default).launch {
            val tags = mapTagList(mument)
            withContext(Dispatchers.Main) {
                holder.binding.rvMumentTags.run {
                    adapter = MumentTagListAdapter()
                    (adapter as MumentTagListAdapter).submitList(tags)
                }
            }
        }
    }

    private fun mapTagList(mument: MumentSummaryEntity): List<TagEntity> {
        val cardTags = mutableListOf<TagEntity>()
        val isFirst = if (mument.isFirst) R.string.tag_is_first else R.string.tag_has_heard
        cardTags.add( TagEntity(TAG_IS_FIRST, isFirst,  if (mument.isFirst) 1 else 0) )
        cardTags.addAll(
            mument.cardTag.map { tagIdx ->
                val type = if (tagIdx < 200) TAG_IMPRESSIVE else TAG_EMOTIONAL
                val tag = if (tagIdx < 200) findImpressiveStringTag(tagIdx) else findEmotionalStringTag(tagIdx)
                TagEntity(type, tag, tagIdx)
            }
        )
        return cardTags
    }

    class MusicDetailMumentListViewHolder(val binding: ItemMusicDetailListBinding): RecyclerView.ViewHolder(binding.root)
}