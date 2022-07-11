package com.mument_android.app.data.network.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.util.MumentDiffCallBack
import com.mument_android.databinding.ItemHeardMumentLayoutBinding

class HeardMumentListAdapter(private val itemClickListener: (MumentCard) -> Unit) :
    ListAdapter<MumentCard, HeardMumentListAdapter.HeardViewHolder>(MumentDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeardViewHolder {
        return HeardViewHolder(
            ItemHeardMumentLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemClickListener
        )
    }

    override fun onBindViewHolder(holder: HeardViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class HeardViewHolder(
        private val binding: ItemHeardMumentLayoutBinding,
        private val itemClickListener: (MumentCard) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: MumentCard) {
            binding.mument = data
            binding.clMument.setOnClickListener {
                itemClickListener(data)
            }
        }
    }
}