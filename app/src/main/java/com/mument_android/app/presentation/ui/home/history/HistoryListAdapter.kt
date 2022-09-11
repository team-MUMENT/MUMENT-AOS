package com.mument_android.app.presentation.ui.home.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.history.MumentHistory
import com.mument_android.app.util.enumtype.EmotionalTag
import com.mument_android.app.util.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.presentation.ui.detail.mument.MumentTagListAdapter
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemMumentLayoutBinding

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