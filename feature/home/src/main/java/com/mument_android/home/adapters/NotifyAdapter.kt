package com.mument_android.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.home.BR
import com.mument_android.home.databinding.ItemNotifyListBinding
import com.mument_android.home.models.Notify

class NotifyAdapter() :
    ListAdapter<Notify, NotifyAdapter.NotifyViewHolder>(GlobalDiffCallBack<Notify>()) {

    lateinit var inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return NotifyViewHolder(ItemNotifyListBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        holder.binding.setVariable(BR.notify, getItem(position))
    }

    class NotifyViewHolder(val binding: ItemNotifyListBinding) : ViewHolder(binding.root)
}