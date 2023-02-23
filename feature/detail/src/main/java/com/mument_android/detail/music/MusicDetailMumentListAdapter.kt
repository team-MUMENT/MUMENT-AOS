package com.mument_android.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.core_dependent.util.myIsDigitsOnly
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemMusicDetailListBinding
import com.mument_android.detail.mument.listener.MumentClickListener
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            checkLikeMument(holder, position)
            setMumentTagList(holder, position)
        }
    }

    private fun checkLikeMument(holder: MusicDetailMumentListViewHolder, position: Int) {
        val mument = getItem(position)
        holder.binding.run {
            laLikeMusic.click {
                val likeCount = tvLikeCount.text.toString().toInt()
                if (laLikeMusic.progress == 0F) {
                    mumentClickListener.likeMument(
                        mument.mumentId
                    )
                    laLikeMusic.playAnimation()
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        mument.isLiked = true
                        tvLikeCount.text = (likeCount + 1).toString()
                        laLikeMusic.progress = 100F
                    }
                } else {
                    mumentClickListener.cancelLikeMument(mument.mumentId)
                    mument.isLiked = false
                    laLikeMusic.progress = 0F
                    tvLikeCount.text = (likeCount - 1).toString()
                }
            }
            tvLikeCount.click {
                if (tvLikeCount.text.myIsDigitsOnly()) {
                    val likeCount = tvLikeCount.text.toString().toInt()
                    if (laLikeMusic.progress == 0F) {
                        mumentClickListener.likeMument(
                            mument.mumentId
                        )
                        laLikeMusic.playAnimation()
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            mument.isLiked = true
                            tvLikeCount.text = (likeCount + 1).toString()
                            laLikeMusic.progress = 100F
                        }
                    } else {
                        mumentClickListener.cancelLikeMument(mument.mumentId)
                        mument.isLiked = false
                        laLikeMusic.progress = 0F
                        tvLikeCount.text = (likeCount - 1).toString()
                    }

                }
            }
        }
    }

    private fun setMumentTagList(holder: MusicDetailMumentListViewHolder, position: Int) {
        val mument = getItem(position)
        holder.binding.rvMumentTags.run {
            adapter = MumentTagListAdapter()
            (adapter as MumentTagListAdapter).submitList(mument.tags)
        }
    }

    class MusicDetailMumentListViewHolder(val binding: ItemMusicDetailListBinding) :
        RecyclerView.ViewHolder(binding.root)
}