package com.mument_android.app.data.network.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemHeardMumentLayoutBinding

class HeardMumentListAdapter(private val itemClickListener: (MumentCard) -> Unit) :
    ListAdapter<MumentCard, HeardMumentListAdapter.HeardViewHolder>(GlobalDiffCallBack<MumentCard>()) {


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
        val mumentData = getItem(position)
        holder.binding.setVariable(BR.mument, mumentData)
        holder.binding.clMument.setOnClickListener {
            itemClickListener(mumentData)
        }
    }

    class HeardViewHolder(
        val binding: ItemHeardMumentLayoutBinding,
        val itemClickListener: (MumentCard) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)
}