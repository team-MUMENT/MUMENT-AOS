package com.mument_android.detail.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.core_dependent.util.myIsDigitsOnly
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemMumentLayoutBinding
import com.mument_android.detail.music.MusicDetailMumentListAdapter
import com.mument_android.domain.entity.history.MumentHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryListAdapter(
    private val itemClickListener: (String) -> Unit,
    private val likeMument: (String) -> Unit,
    private val cancelLikeMument: (String) -> Unit
) : ListAdapter<MumentHistory, HistoryListAdapter.HistoryViewHolder>(object :
    DiffUtil.ItemCallback<MumentHistory>() {
    override fun areItemsTheSame(oldItem: MumentHistory, newItem: MumentHistory): Boolean =
        oldItem._id == newItem._id

    override fun areContentsTheSame(oldItem: MumentHistory, newItem: MumentHistory): Boolean =
        oldItem == newItem
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemMumentLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.rvMumentTags.adapter = MumentTagListAdapter()
        (holder.binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(data.cardTag?.map {
            if (it < 200) TagEntity(
                TagEntity.TAG_IMPRESSIVE,
                ImpressiveTag.findImpressiveStringTag(it),
                it
            )
            else TagEntity(TagEntity.TAG_EMOTIONAL, EmotionalTag.findEmotionalStringTag(it), it)
        })
        holder.binding.root.click {
            itemClickListener(data._id.toString())
        }
        holder.binding.setVariable(BR.mumentHistory, data)
        holder.binding.run {
            laLikeMumentHistory.click {
                val likeCount = tvLikeCount.text.toString().toInt()
                if (laLikeMumentHistory.progress == 0F) {
                    laLikeMumentHistory.playAnimation()
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        data.isLiked = true
                        tvLikeCount.text = (likeCount + 1).toString()
                        laLikeMumentHistory.progress = 100F
                    }
                    likeMument(data._id.toString())
                } else {
                    cancelLikeMument(
                        data._id.toString()
                    )
                    laLikeMumentHistory.progress = 0F
                    data.isLiked = false
                    tvLikeCount.text = (likeCount - 1).toString()
                }
            }
            tvLikeCount.click {
                if (tvLikeCount.text.myIsDigitsOnly()) {
                    val likeCount = tvLikeCount.text.toString().toInt()
                    if (laLikeMumentHistory.progress == 0F) {
                        laLikeMumentHistory.playAnimation()
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            data.isLiked = true
                            tvLikeCount.text = (likeCount + 1).toString()
                            laLikeMumentHistory.progress = 100F
                        }
                        likeMument(data._id.toString())
                    } else {
                        cancelLikeMument(
                            data._id.toString()
                        )
                        data.isLiked = false
                        laLikeMumentHistory.progress = 0F
                        tvLikeCount.text = (likeCount - 1).toString()
                    }
                }
            }
        }
    }

    class HistoryViewHolder(val binding: ItemMumentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}