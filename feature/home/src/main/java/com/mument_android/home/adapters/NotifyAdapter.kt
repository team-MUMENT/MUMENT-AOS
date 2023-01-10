package com.mument_android.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.home.BR
import com.mument_android.home.databinding.ItemNotifyListBinding
import com.mument_android.home.models.Notify

class NotifyAdapter :
    ListAdapter<Notify, NotifyAdapter.NotifyViewHolder>(GlobalDiffCallBack<Notify>()) {

    lateinit var inflater: LayoutInflater
    private val suffix = "...에 쓴 뮤멘트를 좋아합니다"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return NotifyViewHolder(ItemNotifyListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        holder.binding.setVariable(BR.notify, getItem(position))
        /*holder.binding.tvNotifyTitle.viewTreeObserver.addOnGlobalLayoutListener {
            holder.binding.tvNotifyTitle.run {
                post {
                    if (layout.getEllipsisStart(2) != 0) {
                        val newText = text.removeRange(
                            text.length - (layout.getEllipsisCount(2) + (suffix.length)),
                            text.length
                        )
                        text = String.format("%s%s", newText, suffix)
                        requestLayout()
                    }
                }
            }
        }*/
    }

    class NotifyViewHolder(val binding: ItemNotifyListBinding) : ViewHolder(binding.root)
}