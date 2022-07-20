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
import timber.log.Timber


class FilterBottomSheetAdapter(
    val context: Context,
    val checkTagListener: FilterTagCheckListener
//    val checkListener: (TagEntity) -> Unit,
//    val unCheckListener: (TagEntity) -> Unit
) : ListAdapter<TagEntity, FilterBottomSheetAdapter.BottomSheetFilterHolder>(
    GlobalDiffCallBack<TagEntity>()
) {

    interface FilterTagCheckListener {
        fun addCheckedTag(tag: TagEntity): Unit
        fun removeCheckedTag(tag: TagEntity): Unit
        fun alertMaxCount()
    }

    var selectedTags = mutableListOf<TagEntity>()
    var reset: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetFilterHolder {
        val binding =
            ItemTagCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BottomSheetFilterHolder(binding)
    }

    override fun onBindViewHolder(holder: BottomSheetFilterHolder, position: Int) {
        with(holder.binding.flItem.layoutParams as ViewGroup.MarginLayoutParams) {

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
                if (selectedTags.map {
                        it.tagString
                    }.contains(getItem(position).tagString)){
                    checkBox.isChecked = true
                }

                holder.binding.flItem.layoutParams = this
                if (reset) {
                    checkBox.isChecked = false
                    reset = false
                }
            }


        }
        holder.binding.cbTag.setOnCheckedChangeListener { button, isChecked ->
//            //if(isChecked) checkListener(getItem(position)) else unCheckListener(getItem(position))
//            if (isChecked) checkTagListener.addCheckedTag(getItem(position)) else checkTagListener.removeCheckedTag(
//                getItem(position)
//            )
        }

        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }

    class BottomSheetFilterHolder(val binding: ItemTagCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root)
}
