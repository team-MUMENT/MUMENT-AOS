package com.mument_android.record

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.databinding.ItemTagCheckboxBinding
import com.mument_android.core_dependent.util.GlobalDiffCallBack

class RecordTagAdapter(
    val context: Context,
    val option: Boolean,
    val checkTagListener: RecordTagCheckListener
) : ListAdapter<TagEntity, RecordTagAdapter.RecordTagViewHolder>(
    GlobalDiffCallBack<TagEntity>()
) {

    interface RecordTagCheckListener {
        fun addCheckedTag(tag: TagEntity): Unit
        fun removeCheckedTag(tag: TagEntity): Unit
        fun alertMaxCount()
    }

    val selectedTags = mutableListOf<TagEntity>()
    var reset: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordTagViewHolder {
        val binding =
            ItemTagCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordTagViewHolder, position: Int) {
        with(holder.binding.flItem.layoutParams as ViewGroup.MarginLayoutParams) {

            holder.binding.cbTag.let { checkBox ->
                checkBox.setOnClickListener {
                    if (checkBox.isChecked) {
                        if (selectedTags.count() >= 5 && !selectedTags.contains(getItem(position))) {
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

            holder.binding.flItem.layoutParams = this
            }

            if (reset) {
                holder.binding.cbTag.isChecked = false
                reset = false
            }
        }
        holder.binding.cbTag.isChecked = selectedTags.contains(getItem(position))
        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }

    class RecordTagViewHolder(val binding: ItemTagCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root)

}