package com.mument_android.app.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.data.dto.home.Mument
import com.startup.core_dependent.util.GlobalDiffCallBack
import com.startup.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.databinding.ItemImpressiveEmotionMumentLayoutBinding
import timber.log.Timber

class ImpressiveEmotionListAdapter(
    private val context: Context,
    private val itemClickListener: (Mument) -> Unit
) :
    ListAdapter<Mument, ImpressiveEmotionListAdapter.ImpressiveEmotionViewHolder>(
        GlobalDiffCallBack<Mument>()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImpressiveEmotionViewHolder {
        return ImpressiveEmotionViewHolder(
            ItemImpressiveEmotionMumentLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImpressiveEmotionViewHolder, position: Int) {
        val mementData = getItem(position)
        Timber.d("Impress ${getItem(position)}")
        with(holder.binding.clMument.layoutParams as ViewGroup.MarginLayoutParams) {
            marginStart = if (position == 0) 16.dpToPx(context) else 5.dpToPx(context)
            marginEnd = if (position == (itemCount - 1)) 16.dpToPx(context) else 5.dpToPx(context)
            holder.binding.clMument.layoutParams = this
        }
        holder.binding.setVariable(BR.randomMument, mementData)
        holder.binding.clMument.setOnClickListener {
            itemClickListener(mementData)
        }
    }

    class ImpressiveEmotionViewHolder(
        val binding: ItemImpressiveEmotionMumentLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root)


}