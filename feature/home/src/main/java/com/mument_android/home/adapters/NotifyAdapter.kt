package com.mument_android.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.home.BR
import com.mument_android.home.databinding.ItemNotifyListBinding
import com.mument_android.home.models.Notify

class NotifyAdapter(val onNotifyClick: (Notify) -> Unit, val onDeleteClick: (Notify) -> Unit) :
    ListAdapter<Notify, NotifyAdapter.NotifyViewHolder>(GlobalDiffCallBack<Notify>()) {

    private lateinit var inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return NotifyViewHolder(ItemNotifyListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.setVariable(BR.notify, data)
        holder.binding.root.setOnClickListener {
            onNotifyClick(data)
        }
        holder.binding.ivNotifyDelete.setOnClickListener {
            onDeleteClick(data)
        }
    }

    override fun onViewRecycled(holder: NotifyViewHolder) {
        holder.binding.tvNotifyTitle.spanCountIncrease()
        super.onViewRecycled(holder)
    }

    class NotifyViewHolder(val binding: ItemNotifyListBinding) : ViewHolder(binding.root)
}