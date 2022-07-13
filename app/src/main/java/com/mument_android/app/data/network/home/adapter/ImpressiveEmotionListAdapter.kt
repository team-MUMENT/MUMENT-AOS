package com.mument_android.app.data.network.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.databinding.ItemImpressiveEmotionMumentLayoutBinding

class ImpressiveEmotionListAdapter(private val context: Context, private val itemClickListener: (MumentCard) -> Unit) :
    ListAdapter<MumentCard, ImpressiveEmotionListAdapter.ImpressiveEmotionViewHolder>(
        GlobalDiffCallBack<MumentCard>()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImpressiveEmotionViewHolder {
        return ImpressiveEmotionViewHolder(
            ItemImpressiveEmotionMumentLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ImpressiveEmotionViewHolder, position: Int) {
        val mementData = getItem(position)
        with(holder.binding.clMument.layoutParams as ViewGroup.MarginLayoutParams){
            marginStart = if (position == 0) 16.dpToPx(context) else 5.dpToPx(context)
            marginEnd = if (position == 0) 5.dpToPx(context) else 16.dpToPx(context)
            holder.binding.clMument.layoutParams = this
        }
        holder.binding.setVariable(BR.mument, mementData)
        holder.binding.clMument.setOnClickListener {
            itemClickListener(mementData)
        }
    }

    class ImpressiveEmotionViewHolder(
        val binding: ItemImpressiveEmotionMumentLayoutBinding,
        val itemClickListener: (MumentCard) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)


}