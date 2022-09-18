package com.startup.detail.mument

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.domain.entity.TagEntity
import com.startup.core_dependent.util.GlobalDiffCallBack
import com.startup.detail.BR
import com.startup.detail.databinding.ItemMumentStringTagBinding

class MumentTagListAdapter: ListAdapter<TagEntity, MumentTagListAdapter.MumentTagListViewHolder>(
    GlobalDiffCallBack<TagEntity>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MumentTagListViewHolder {
        val mumentStringTagBinding = ItemMumentStringTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MumentTagListViewHolder(mumentStringTagBinding)
    }

    override fun onBindViewHolder(holder: MumentTagListViewHolder, position: Int) {
        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }

    class MumentTagListViewHolder(val binding: ItemMumentStringTagBinding): RecyclerView.ViewHolder(binding.root)
}