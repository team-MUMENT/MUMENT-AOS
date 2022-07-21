package com.mument_android.app.presentation.ui.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.R
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.detail.MumentSummaryEntity
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.presentation.ui.detail.mument.MumentClickListener
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemMusicDetailListBinding

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
            root.setOnClickListener { mumentClickListener.showMumentDetail(getItem(position).mumentId) }
            checkLikeMument(holder)
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
                val likeCountText = tvLikeCount.context.resources.getString(R.string.like_count_integer_format, updatedLikeCount)
                tvLikeCount.text = likeCountText
            }
        }
    }

    class MusicDetailMumentListViewHolder(val binding: ItemMusicDetailListBinding): RecyclerView.ViewHolder(binding.root)
}