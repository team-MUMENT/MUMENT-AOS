package com.mument_android.app.data.network.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemMumentLayoutBinding

class HistoryListAdapter(val context: Context) :
    ListAdapter<MusicDetailEntity, HistoryListAdapter.HistoryViewHolder>(GlobalDiffCallBack<MusicDetailEntity>()) {

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
        holder.binding.setVariable(BR.musicDetail, getItem(position))
    }

    class HistoryViewHolder(
        val binding: ItemMumentLayoutBinding,
    ) :
        RecyclerView.ViewHolder(binding.root)
}