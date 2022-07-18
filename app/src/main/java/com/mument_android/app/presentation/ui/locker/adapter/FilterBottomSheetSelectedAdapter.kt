package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemMumentFilterStringTagBinding
import timber.log.Timber

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
            Timber.d("Adapter Test Code : $tag")
            removeSelectedTagListener(tag, currentList.indexOf(tag))
        }
    }

    class BottomSheetFilterTagHolder(val binding: ItemMumentFilterStringTagBinding) : RecyclerView.ViewHolder(binding.root)

}