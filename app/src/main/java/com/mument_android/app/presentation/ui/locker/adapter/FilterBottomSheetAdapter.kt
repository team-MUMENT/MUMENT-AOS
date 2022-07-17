package com.mument_android.app.presentation.ui.locker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemTagCheckboxBinding


class FilterBottomSheetAdapter(
    val context: Context,
    val checkListener: (TagEntity) -> Unit,
    val unCheckListener: (TagEntity) -> Unit
) : ListAdapter<TagEntity, FilterBottomSheetAdapter.BottomSheetFilterHolder>(
    GlobalDiffCallBack<TagEntity>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetFilterHolder {
        val binding =
            ItemTagCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetFilterHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetFilterHolder, position: Int) {
        with(holder.binding.flItem.layoutParams as ViewGroup.MarginLayoutParams) {
            holder.binding.flItem.layoutParams = this
        }
        holder.binding.cbTag.setOnCheckedChangeListener{button, isChecked ->
            if(isChecked) checkListener(getItem(position)) else unCheckListener(getItem(position))
        }

        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }

    class BottomSheetFilterHolder(val binding: ItemTagCheckboxBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun addItem(data:TagEntity){
        val list= currentList.toMutableList()
        list.add(data)
        submitList(list)
    }


}
