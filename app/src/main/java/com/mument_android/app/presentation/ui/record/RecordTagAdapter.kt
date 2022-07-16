package com.mument_android.app.presentation.ui.record

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.databinding.ItemTagCheckboxBinding

class RecordTagAdapter(
    val context: Context,
    val option: Boolean,
    val checkListener: (TagEntity) -> Unit,
    val unCheckListener: (TagEntity) -> Unit
    ) :
    ListAdapter<TagEntity, RecordTagAdapter.RecordTagViewHolder>(
        GlobalDiffCallBack<TagEntity>()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordTagViewHolder {
        val binding =
            ItemTagCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordTagViewHolder, position: Int) {
        with(holder.binding.flItem.layoutParams as ViewGroup.MarginLayoutParams) {
            marginStart =
                when (option) {
                    true -> if (position == 0 || position == 6 || position == 11) 16.dpToPx(context) else 0.dpToPx(
                        context
                    )
                    false -> 0
                }


            holder.binding.flItem.layoutParams = this

            holder.binding.cbTag.setOnCheckedChangeListener { button, isChecked ->
                if (isChecked) checkListener(getItem(position)) else unCheckListener(getItem(position))
            }
        }

        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }

    class RecordTagViewHolder(val binding: ItemTagCheckboxBinding) : RecyclerView.ViewHolder(binding.root)

}