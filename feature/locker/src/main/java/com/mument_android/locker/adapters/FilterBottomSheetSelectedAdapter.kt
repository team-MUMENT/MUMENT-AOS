package com.mument_android.locker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.locker.BR
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.locker.databinding.ItemMumentFilterStringTagBinding

class FilterBottomSheetSelectedAdapter(
    private val removeSelectedTagListener: (TagEntity, Int) -> Unit
) : ListAdapter<TagEntity, FilterBottomSheetSelectedAdapter.BottomSheetFilterTagHolder>(
    GlobalDiffCallBack<TagEntity>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetFilterTagHolder {
        val binding = ItemMumentFilterStringTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetFilterTagHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetFilterTagHolder, position: Int) {
        holder.binding.setVariable(BR.tagEntity, getItem(position))
        val tag = getItem(position)
        holder.binding.tvSelectedTag.setOnClickListener {
            removeSelectedTagListener(tag, currentList.indexOf(tag))
        }
    }

    class BottomSheetFilterTagHolder(val binding: ItemMumentFilterStringTagBinding) : RecyclerView.ViewHolder(binding.root)

}