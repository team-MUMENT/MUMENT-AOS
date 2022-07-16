package com.mument_android.app.presentation.ui.record

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mument_android.BR
import com.mument_android.R
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.ViewUtils.showToast
import com.mument_android.app.util.ViewUtils.snackBar
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
    var enabled: Boolean = true
    var reset: Boolean = false
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
                if (enabled) {
                    if (isChecked) {
                        checkListener(getItem(position))
                    } else {
                        unCheckListener(getItem(position))
                    }
                } else {
                    if (!isChecked) {
                        unCheckListener(getItem(position))
                    }
                    holder.binding.cbTag.isChecked = false
                    context.snackBar(
                        holder.binding.root as ViewGroup,
                        "\'처음들어요\'는 한 곡당 한 번만 선택할 수 있어요"
                    )
                }
            }
            if (reset) {
                holder.binding.cbTag.isChecked = false
                reset = false
            }

        }
        holder.binding.setVariable(BR.tagEntity, getItem(position))
    }

    class RecordTagViewHolder(val binding: ItemTagCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root)

}