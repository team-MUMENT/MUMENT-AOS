package com.mument_android.app.presentation.ui.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemEmotionalTagBinding


class RecordTagAdapter: ListAdapter<Int, RecordTagAdapter.RecordTagViewHolder>(
    GlobalDiffCallBack<Int>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordTagViewHolder {
        val binding = ItemEmotionalTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordTagViewHolder, position: Int) {
        holder.binding.setVariable(BR.tagData, getItem(position))
    }

    class RecordTagViewHolder(val binding: ItemEmotionalTagBinding):RecyclerView.ViewHolder(binding.root){

    }
}