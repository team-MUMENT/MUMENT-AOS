package com.mument_android.core_dependent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.BR
import com.mument_android.core_dependent.databinding.ItemMumentStringTagBinding
import com.mument_android.core_dependent.util.GlobalDiffCallBack

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