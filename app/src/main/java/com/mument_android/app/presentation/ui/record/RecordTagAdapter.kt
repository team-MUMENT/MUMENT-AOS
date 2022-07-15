package com.mument_android.app.presentation.ui.record

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemTagCheckboxBinding

class RecordTagAdapter(val itemClickListener: (TagEntity) -> Unit) : ListAdapter<TagEntity, RecordTagAdapter.RecordTagViewHolder>(
        GlobalDiffCallBack<TagEntity>()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordTagViewHolder {
        val binding = ItemTagCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordTagViewHolder, position: Int) {
        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }

    class RecordTagViewHolder(val binding: ItemTagCheckboxBinding):RecyclerView.ViewHolder(binding.root)

}