package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.app.domain.entity.TestLockerMumentCard
import com.mument_android.databinding.ItemLockerDateBinding

class LockerTimeAdapter : RecyclerView.Adapter<LockerTimeAdapter.LockerTimeViewHolder>() {
    var data = mutableListOf<TestLockerMumentCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LockerTimeViewHolder {
        val binding = ItemLockerDateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LockerTimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerTimeViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class LockerTimeViewHolder(
        val binding: ItemLockerDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data : TestLockerMumentCard) {
            binding.apply {
                mumentDate = data
                executePendingBindings()
            }
        }
    }

    fun setTime(data : MutableList<TestLockerMumentCard>) {
        this.data = data
        notifyDataSetChanged()
    }

}