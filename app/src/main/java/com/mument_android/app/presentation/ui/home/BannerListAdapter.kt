package com.mument_android.app.presentation.ui.home

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.startup.domain.entity.home.BannerEntity
import com.mument_android.databinding.ItemBannerLayoutBinding

class BannerListAdapter(var data: List<BannerEntity>, private val clickBanner: (String) -> Unit) : RecyclerView.Adapter<BannerListAdapter.BannerViewHolder>() {

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
                clickBanner(data[position % data.size].music._id)
            }
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}