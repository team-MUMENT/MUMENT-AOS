package com.mument_android.app.data.network.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.data.dto.home.AgainMument
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.databinding.ItemHeardMumentLayoutBinding
import timber.log.Timber

class HeardMumentListAdapter(
    private val context: Context,
    private val itemClickListener: (AgainMument) -> Unit
) :
    ListAdapter<AgainMument, HeardMumentListAdapter.HeardViewHolder>(GlobalDiffCallBack<AgainMument>()) {


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

        Timber.d("Again ${getItem(position)}")
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