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
import com.mument_android.app.data.dto.home.Banner
import com.mument_android.app.domain.entity.TempBannerData
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemBannerLayoutBinding

class BannerListAdapter(var data: List<Banner>) :
    RecyclerView.Adapter<BannerListAdapter.BannerViewHolder>() {


    val albumImage = listOf<Int>(
        R.drawable.mument_banner_1,
        R.drawable.mument_banner_2,
        R.drawable.mument_banner_3
    )

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
        if (data.isNotEmpty()) {
            val builder =
                SpannableStringBuilder((position % data.size + 1).toString() + " / " + data.size)
            val span = ForegroundColorSpan(Color.WHITE)
            builder.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            holder.binding.banner = (data[position % data.size])
            holder.binding.position = (position % data.size).toString()
            holder.binding.tvIndex.text = builder
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}