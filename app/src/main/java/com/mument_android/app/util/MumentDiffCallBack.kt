package com.mument_android.app.util

import androidx.recyclerview.widget.DiffUtil
import com.mument_android.app.domain.entity.MumentCard

object MumentDiffCallBack:DiffUtil.ItemCallback<MumentCard>() {
    override fun areItemsTheSame(oldItem: MumentCard, newItem: MumentCard): Boolean {
        return (oldItem._id == newItem._id) && (oldItem.user._id == newItem.user._id)
    }

    override fun areContentsTheSame(oldItem: MumentCard, newItem: MumentCard): Boolean {
        return oldItem == newItem
    }
}