package com.mument_android.app.data.network.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.marginLeft
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.R
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.ViewUtils.pxToDp
import com.mument_android.databinding.ItemHeardMumentLayoutBinding
import timber.log.Timber

class HeardMumentListAdapter(
    private val context: Context,
    private val itemClickListener: (MumentCard) -> Unit
) :
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
        with(holder.binding.clMument.layoutParams as ViewGroup.MarginLayoutParams){
            marginStart = if (position == 0) 16.dpToPx(context) else 5.dpToPx(context)
            marginEnd = if (position == 0) 5.dpToPx(context) else 16.dpToPx(context)
            holder.binding.clMument.layoutParams = this
        }
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