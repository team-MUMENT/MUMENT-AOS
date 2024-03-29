package com.mument_android.home.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.domain.entity.home.AgainMumentEntity
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.home.BR
import com.mument_android.home.databinding.ItemHeardMumentLayoutBinding
import javax.inject.Inject

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
            //홈 탭에서 다시 들은 곡 터치 GA
            FirebaseAnalyticsUtil.firebaseLog(
                "home_activity_type",
                "type",
                "home_relistenmu"
            )

            //뮤멘트 상세보기에 진입했을 때 GA
            FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_home")
        }
    }

    class HeardViewHolder(
        val binding: ItemHeardMumentLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}