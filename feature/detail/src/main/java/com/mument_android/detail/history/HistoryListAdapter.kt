package com.mument_android.detail.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.*
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemMumentLayoutBinding
import com.mument_android.domain.entity.history.MumentHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryListAdapter(
    private val itemClickListener: (String) -> Unit,
    private val likeMumentListener: LikeMumentListener,
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
            //뮤멘트 상세보기에 진입했을 때 GA
            FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_history_list")
        }
        holder.binding.setVariable(BR.mumentHistory, data)
        holder.binding.run {
            laLikeMumentHistory.click {
                val likeCount = tvLikeCount.text.toString().toInt()
                laLikeMumentHistory.isClickable = false
                if (laLikeMumentHistory.progress == 0F) {
                    laLikeMumentHistory.playAnimation()
                    likeMumentListener.likeMument(data._id.toString()) { result ->
                        if (result) {
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1000)
                                data.isLiked = true
                                tvLikeCount.text = (likeCount + 1).toString()
                                laLikeMumentHistory.progress = 100F
                                laLikeMumentHistory.isClickable = true
                            }
                        } else {
                            laLikeMumentHistory.progress = 0F
                            holder.binding.root.context.showToast("오류가 발생했습니다.")
                            laLikeMumentHistory.isClickable = true
                        }
                    }
                } else {
                    likeMumentListener.cancelLikeMument(data._id.toString()) { result ->
                        if (result) {
                            laLikeMumentHistory.progress = 0F
                            data.isLiked = false
                            tvLikeCount.text = (likeCount - 1).toString()
                        } else {
                            holder.binding.root.context.showToast("오류가 발생했습니다.")
                        }
                        laLikeMumentHistory.isClickable = true
                    }
                }
            }
        }
    }

    class HistoryViewHolder(val binding: ItemMumentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}