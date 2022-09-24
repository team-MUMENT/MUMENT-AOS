package com.startup.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.domain.entity.home.AgainMumentEntity
import com.startup.core_dependent.util.GlobalDiffCallBack
import com.startup.core_dependent.util.ViewUtils.dpToPx
import com.startup.home.BR
import com.startup.home.databinding.ItemHeardMumentLayoutBinding

class HeardMumentListAdapter(
    private val context: Context,
    private val itemClickListener: (AgainMumentEntity) -> Unit
) :
    ListAdapter<AgainMumentEntity, HeardMumentListAdapter.HeardViewHolder>(GlobalDiffCallBack<AgainMumentEntity>()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeardViewHolder {
        return HeardViewHolder(
            ItemHeardMumentLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeardViewHolder, position: Int) {
        val mumentData = getItem(position)
        with(holder.binding.clMument.layoutParams as ViewGroup.MarginLayoutParams) {
            marginStart = if (position == 0) 16.dpToPx(context) else 5.dpToPx(context)
            marginEnd = if (position == (itemCount - 1)) 16.dpToPx(context) else 5.dpToPx(context)
            holder.binding.clMument.layoutParams = this
        }
        holder.binding.setVariable(BR.againMument, mumentData)
        holder.binding.clMument.setOnClickListener {
            itemClickListener(mumentData)
        }
    }

    class HeardViewHolder(
        val binding: ItemHeardMumentLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}