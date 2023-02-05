package com.mument_android.detail.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemMumentLayoutBinding
import com.mument_android.domain.entity.history.MumentHistory

class HistoryListAdapter(private val itemClickListener: (MumentHistory) -> Unit) :
    PagingDataAdapter<MumentHistory, HistoryListAdapter.HistoryViewHolder>(object :
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
        if (data != null) {
            holder.binding.rvMumentTags.adapter = MumentTagListAdapter()
            (holder.binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(data.cardTag?.map {
                if (it < 200) TagEntity(
                    TagEntity.TAG_IMPRESSIVE,
                    ImpressiveTag.findImpressiveStringTag(it),
                    it
                )
                else TagEntity(TagEntity.TAG_EMOTIONAL, EmotionalTag.findEmotionalStringTag(it), it)
            })
            holder.binding.root.setOnClickListener {
                itemClickListener(data)
            }
            holder.binding.setVariable(BR.musicDetail, data)
        }
    }

    class HistoryViewHolder(val binding: ItemMumentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}