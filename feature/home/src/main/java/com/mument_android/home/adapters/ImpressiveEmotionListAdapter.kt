package com.mument_android.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.domain.entity.home.Mument
import com.mument_android.home.BR
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.home.databinding.ItemImpressiveEmotionMumentLayoutBinding

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
        with(holder.binding.clMument.layoutParams as ViewGroup.MarginLayoutParams) {
            marginStart = if (position == 0) 16.dpToPx(context) else 5.dpToPx(context)
            marginEnd = if (position == (itemCount - 1)) 16.dpToPx(context) else 5.dpToPx(context)
            holder.binding.clMument.layoutParams = this
        }
        holder.binding.setVariable(BR.randomMument, mementData)
        holder.binding.clMument.setOnClickListener {
            itemClickListener(mementData)
            //홈탭에서 -> 태그를 느낀 순간 터치
            FirebaseAnalyticsUtil.firebaseLog(
                "home_activity_type",
                "type",
                "home_tagmu"
            )

            //뮤멘트 상세보기에 진입했을 때 GA
            FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_home")

        }
    }

    class ImpressiveEmotionViewHolder(
        val binding: ItemImpressiveEmotionMumentLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root)


}