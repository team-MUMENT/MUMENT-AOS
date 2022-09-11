package com.mument_android.app.presentation.ui.detail.mument

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemMumentStringTagBinding

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