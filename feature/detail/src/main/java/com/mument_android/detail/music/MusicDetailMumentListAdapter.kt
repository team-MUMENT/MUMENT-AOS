package com.mument_android.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.core_dependent.util.myIsDigitsOnly
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemMusicDetailListBinding
import com.mument_android.detail.mument.listener.MumentClickListener
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.*

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
                //뮤멘트 상세보기에 진입했을 때 GA
                FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_song_detail_page")
            }
            checkLikeMument(holder, position)
            setMumentTagList(holder, position)
        }
    }

    private fun checkLikeMument(holder: MusicDetailMumentListViewHolder, position: Int) {
        val mument = getItem(position)
        holder.binding.run {
            llLikeTouchArea.click {
                if (tvLikeCount.text.myIsDigitsOnly()) {
                    val likeCount = tvLikeCount.text.toString().toInt()
                    llLikeTouchArea.isClickable = false
                    if (laLikeMusic.progress == 0F) {
                        laLikeMusic.playAnimation()
                        val job = CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            tvLikeCount.text = (likeCount + 1).toString()
                            mument.isLiked = true
                            laLikeMusic.progress = 100F
                            llLikeTouchArea.isClickable = true
                        }
                        mumentClickListener.likeMument(mument.mumentId) { result ->
                            if (!result) {
                                job.cancel()
                                holder.binding.root.context.showToast("오류가 발생했습니다.")
                                laLikeMusic.progress = 0F
                                tvLikeCount.text = likeCount.toString()
                                llLikeTouchArea.isClickable = true
                            }
                        }
                    } else {
                        mument.isLiked = false
                        laLikeMusic.progress = 0F
                        tvLikeCount.text = (likeCount - 1).toString()
                        mumentClickListener.cancelLikeMument(mument.mumentId) { result ->
                            if (!result) {
                                holder.binding.root.context.showToast("오류가 발생했습니다.")
                                mument.isLiked = true
                                laLikeMusic.progress = 100F
                                tvLikeCount.text = likeCount.toString()
                            }
                            llLikeTouchArea.isClickable = true
                        }
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