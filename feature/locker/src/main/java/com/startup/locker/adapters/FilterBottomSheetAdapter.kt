package com.startup.locker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.core.model.TagEntity
import com.startup.core_dependent.BR
import com.startup.core_dependent.databinding.ItemTagCheckboxBinding
import com.startup.core_dependent.util.GlobalDiffCallBack


class FilterBottomSheetAdapter(
    val context: Context,
    val checkTagListener: FilterTagCheckListener
) : ListAdapter<TagEntity, FilterBottomSheetAdapter.BottomSheetFilterHolder>(
    GlobalDiffCallBack<TagEntity>()
) {

    interface FilterTagCheckListener {
        fun addCheckedTag(tag: TagEntity): Unit
        fun removeCheckedTag(tag: TagEntity): Unit
        fun alertMaxCount()
    }

    val selectedTags = mutableListOf<TagEntity>()
    var reset: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetFilterHolder {
        val binding =
            ItemTagCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetFilterHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetFilterHolder, position: Int) {
        holder.binding.setVariable(BR.tagEntity, getItem(position))

        holder.binding.cbTag.let { checkBox ->
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    if (selectedTags.count() >= 3 && !selectedTags.contains(getItem(position))) {
                        checkBox.isChecked = false
                        checkTagListener.alertMaxCount()
                    } else {
                        selectedTags.add(getItem(position))
                        checkTagListener.addCheckedTag(getItem(position))
                    }
                } else {
                    selectedTags.remove(getItem(position))
                    checkTagListener.removeCheckedTag(getItem(position))
                }
            }

            if (selectedTags.map { it.tagString }.contains(getItem(position).tagString)) {
                checkBox.isChecked = true
            }

            if (reset) {
                checkBox.isChecked = false
                reset = false
            }
        }
    }

    class BottomSheetFilterHolder(val binding: ItemTagCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root)
}
