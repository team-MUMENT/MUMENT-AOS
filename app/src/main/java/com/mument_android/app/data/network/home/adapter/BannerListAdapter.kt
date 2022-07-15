package com.mument_android.app.data.network.home.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.R
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
        val builder = SpannableStringBuilder((position+1).toString()+" / "+itemCount)
        val span = ForegroundColorSpan(Color.WHITE)
        builder.setSpan(span, 0,1,Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        holder.binding.setVariable(BR.banner, getItem(position))
        holder.binding.tvIndex.text = builder
    }
}