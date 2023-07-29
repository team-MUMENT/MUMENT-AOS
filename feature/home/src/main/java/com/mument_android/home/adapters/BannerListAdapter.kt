package com.mument_android.home.adapters

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.musicdetail.Music
import com.mument_android.home.databinding.ItemBannerLayoutBinding

class BannerListAdapter(var data: List<BannerEntity>, private val clickBanner: (Music) -> Unit) :
    RecyclerView.Adapter<BannerListAdapter.BannerViewHolder>() {



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
            holder.binding.position = (position % data.size)
            holder.binding.tvIndex.text = builder
            holder.binding.root.setOnClickListener {
                clickBanner(data[position % data.size].music)
            }
        } else {
            val builder =
                SpannableStringBuilder((position % 3 + 1).toString() + " / " + 3)
            val span = ForegroundColorSpan(Color.WHITE)
            builder.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            holder.binding.position = (position % 3)
            holder.binding.tvIndex.text = builder
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}