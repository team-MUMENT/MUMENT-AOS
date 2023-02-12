package com.mument_android.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemMusicDetailListBinding
import com.mument_android.detail.mument.listener.MumentClickListener
import com.mument_android.domain.entity.detail.MumentSummaryEntity

class MusicDetailMumentListAdapter(private val mumentClickListener: MumentClickListener) :
    ListAdapter<MumentSummaryEntity, MusicDetailMumentListAdapter.MusicDetailMumentListViewHolder>(
        GlobalDiffCallBack<MumentSummaryEntity>()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicDetailMumentListViewHolder {
        val binding =
            ItemMusicDetailListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicDetailMumentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicDetailMumentListViewHolder, position: Int) {
        holder.binding.apply {
            val data = getItem(position)
            setVariable(BR.mumentSummary, data)
            viewClickable.setOnClickListener {
                mumentClickListener.showMumentDetail(data.mumentId)
            }
            checkLikeMument(holder)
            setMumentTagList(holder)
        }
    }

    private fun checkLikeMument(holder: MusicDetailMumentListViewHolder) {
        val mument = getItem(holder.absoluteAdapterPosition)
        holder.binding.run {
            cbHeart.setOnClickListener {
                val likeCount = mument.likeCount
                if (cbHeart.isChecked) mumentClickListener.likeMument(mument.mumentId) else mumentClickListener.cancelLikeMument(
                    mument.mumentId
                )
                val updatedLikeCount = when {
                    mument.isLiked && cbHeart.isChecked -> likeCount
                    mument.isLiked && !cbHeart.isChecked -> likeCount - 1
                    !mument.isLiked && cbHeart.isChecked -> likeCount + 1
                    !mument.isLiked && !cbHeart.isChecked -> likeCount
                    else -> likeCount
                }
                tvLikeCount.text = updatedLikeCount.toString()
            }
            tvLikeCount.click {
                cbHeart.isChecked = !cbHeart.isChecked
                val likeCount = mument.likeCount
                if (cbHeart.isChecked) mumentClickListener.likeMument(mument.mumentId) else mumentClickListener.cancelLikeMument(
                    mument.mumentId
                )
                val updatedLikeCount = when {
                    mument.isLiked && cbHeart.isChecked -> likeCount
                    mument.isLiked && !cbHeart.isChecked -> likeCount - 1
                    !mument.isLiked && cbHeart.isChecked -> likeCount + 1
                    !mument.isLiked && !cbHeart.isChecked -> likeCount
                    else -> likeCount
                }
                tvLikeCount.text = updatedLikeCount.toString()
            }
        }
    }

    private fun setMumentTagList(holder: MusicDetailMumentListViewHolder) {
        val mument = getItem(holder.absoluteAdapterPosition)
        holder.binding.rvMumentTags.run {
            adapter = MumentTagListAdapter()
            (adapter as MumentTagListAdapter).submitList(mument.tags)
        }
    }

    class MusicDetailMumentListViewHolder(val binding: ItemMusicDetailListBinding) :
        RecyclerView.ViewHolder(binding.root)
}