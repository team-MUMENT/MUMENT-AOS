package com.mument_android.app.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemEmotionalTagBinding
import com.mument_android.databinding.ItemIsFirstTagBinding
import timber.log.Timber


class EmotionalTagListAdapter: ListAdapter<TagEntity, RecyclerView.ViewHolder>(
    GlobalDiffCallBack<TagEntity>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val isFirstTagBinding = ItemIsFirstTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val emotionalTagBinding = ItemEmotionalTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == 0) IsFirstTagViewHolder(isFirstTagBinding) else EmotionalTagViewHolder(emotionalTagBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is EmotionalTagViewHolder -> {
                Timber.e("emotional tag: ${getItem(position)}")
                holder.binding.setVariable(BR.tagEntity, getItem(position))
            }
            is IsFirstTagViewHolder -> {
                Timber.e("is first tag: ${getItem(position)}")
                holder.binding.setVariable(BR.isFirstTag, getItem(position))
            }
            else -> throw IllegalArgumentException("onBindViewHolder Error")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).tagType == TAG_IS_FIRST) 0 else 1
    }

    class IsFirstTagViewHolder(val binding: ItemIsFirstTagBinding): RecyclerView.ViewHolder(binding.root)
    class EmotionalTagViewHolder(val binding: ItemEmotionalTagBinding): RecyclerView.ViewHolder(binding.root)
}