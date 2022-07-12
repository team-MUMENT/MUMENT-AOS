package com.mument_android.app.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.mument_android.app.domain.entity.MumentCard

class GlobalDiffCallBack<T : Any>:DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}