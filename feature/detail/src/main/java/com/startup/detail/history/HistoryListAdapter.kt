package com.startup.detail.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.domain.entity.TagEntity
import com.startup.domain.entity.history.MumentHistory
import com.startup.detail.mument.MumentTagListAdapter
import com.startup.core_dependent.util.EmotionalTag
import com.startup.core_dependent.util.GlobalDiffCallBack
import com.startup.core_dependent.util.ImpressiveTag
import com.startup.detail.BR
import com.startup.detail.databinding.ItemMumentLayoutBinding

class HistoryListAdapter(val context: Context) :
    ListAdapter<MumentHistory, HistoryListAdapter.HistoryViewHolder>(GlobalDiffCallBack<MumentHistory>()) {

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
        val data = getItem(position).cardTag.map {
            if(it < 200) TagEntity(TagEntity.TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(it), it)
            else TagEntity(TagEntity.TAG_EMOTIONAL, EmotionalTag.findEmotionalStringTag(it), it)
        }

        holder.binding.rvMumentTags.adapter = MumentTagListAdapter()
        (holder.binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(data)
        holder.binding.setVariable(BR.musicDetail, getItem(position))
    }

    class HistoryViewHolder(val binding: ItemMumentLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}