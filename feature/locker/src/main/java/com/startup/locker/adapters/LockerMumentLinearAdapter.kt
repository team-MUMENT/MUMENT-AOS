package com.startup.locker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.startup.locker.BR
import androidx.recyclerview.widget.RecyclerView
import com.startup.core.model.TagEntity
import com.startup.core_dependent.ui.MumentTagListAdapter
import com.startup.domain.entity.locker.LockerMumentEntity
import com.startup.core_dependent.util.EmotionalTag
import com.startup.core_dependent.util.GlobalDiffCallBack
import com.startup.core_dependent.util.ImpressiveTag
import com.startup.locker.LikeMumentListener
import com.startup.locker.databinding.ItemLockerCardBinding

//자식어뎁터
class LockerMumentLinearAdapter(
    private val showDetailListener: (String) -> Unit,
    private val likeMumentListener: LikeMumentListener
) : ListAdapter<LockerMumentEntity.MumentLockerCard, LockerMumentLinearAdapter.MumentViewHolder>(
    GlobalDiffCallBack<LockerMumentEntity.MumentLockerCard>()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MumentViewHolder {
        val binding = ItemLockerCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MumentViewHolder, position: Int) {
        val data = getItem(position).cardTag?.map {
            if(it < 200) TagEntity(TagEntity.TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(it), it)
            else TagEntity(TagEntity.TAG_EMOTIONAL, EmotionalTag.findEmotionalStringTag(it), it)
        }
        holder.binding.rvMumentTag.adapter = MumentTagListAdapter()
        (holder.binding.rvMumentTag.adapter as MumentTagListAdapter).submitList(data)
        holder.binding.setVariable(BR.mument, getItem(position))

        likeMument(holder)
        holder.binding.root.setOnClickListener {
            getItem(position)._id?.let { name -> showDetailListener(name) }
        }
    }

    private fun likeMument(holder: MumentViewHolder) {
        val mument = getItem(holder.absoluteAdapterPosition)
        val isLiked = mument.isLiked
        val likeCount = mument.likeCount ?: 0
        holder.binding.run {
            ivLike.setOnClickListener {
                tvLikeCount.text = when {
                    isLiked == true && ivLike.isChecked -> likeCount.toString()
                    isLiked == true && !ivLike.isChecked -> (likeCount-1).toString()
                    isLiked == false && ivLike.isChecked -> (likeCount+1).toString()
                    isLiked == false && !ivLike.isChecked -> likeCount.toString()
                    else -> likeCount.toString()
                }
                if (ivLike.isChecked) likeMumentListener.likeMument(mument._id ?: "") else likeMumentListener.cancelLikeMument(mument._id ?: "")
            }
        }
    }

    class MumentViewHolder(val binding: ItemLockerCardBinding) : RecyclerView.ViewHolder(binding.root)
}