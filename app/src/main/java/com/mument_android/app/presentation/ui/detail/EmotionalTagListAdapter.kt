package com.mument_android.app.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemEmotionalTagBinding

class EmotionalTagListAdapter: ListAdapter<String, EmotionalTagListAdapter.EmotionalTagViewHolder>(
    GlobalDiffCallBack<String>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionalTagViewHolder {
        return EmotionalTagViewHolder(
            ItemEmotionalTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EmotionalTagViewHolder, position: Int) {
        holder.binding.setVariable(BR.tagData, getItem(position))
    }

    class EmotionalTagViewHolder(val binding: ItemEmotionalTagBinding): RecyclerView.ViewHolder(binding.root)
}