package com.mument_android.app.data.network.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.TempBannerData
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemBannerLayoutBinding

class BannerListAdapter :
    ListAdapter<TempBannerData, BannerListAdapter.BannerViewHolder>(GlobalDiffCallBack<TempBannerData>()) {


    class BannerViewHolder(val binding: ItemBannerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemBannerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.binding.setVariable(BR.banner, getItem(position))
    }
}