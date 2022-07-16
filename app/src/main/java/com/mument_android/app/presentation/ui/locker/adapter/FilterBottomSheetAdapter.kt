package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemImpressiveEmotionMumentLayoutBinding
import com.mument_android.databinding.ItemTagCheckboxBinding


class FilterBottomSheetAdapter : ListAdapter<TagEntity, FilterBottomSheetAdapter.BottomSheetFilterHolder>(
    GlobalDiffCallBack<TagEntity>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetFilterHolder {
        val binding = ItemTagCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetFilterHolder(binding)
    }


    class BottomSheetFilterHolder(val binding: ItemTagCheckboxBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: BottomSheetFilterHolder, position: Int) {
        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }
}
