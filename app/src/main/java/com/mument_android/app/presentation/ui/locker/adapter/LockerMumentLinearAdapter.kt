package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.locker.LockerMumentEntity
import com.mument_android.app.presentation.ui.detail.mument.MumentTagListAdapter
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemLockerCardBinding
import timber.log.Timber

//자식어뎁터
class LockerMumentLinearAdapter(
    private val showDetailListener: (String) -> Unit
) : ListAdapter<LockerMumentEntity.MumentLockerCard, LockerMumentLinearAdapter.MumentViewHolder>(
    GlobalDiffCallBack<LockerMumentEntity.MumentLockerCard>()
) {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(user : String, postion : Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener) {
        mlistener = listener
    }

    fun plus(count : Int, like : Boolean, postion: Int) {
        getItem(postion).isLiked = like
        getItem(postion).likeCount = count
        notifyDataSetChanged()
    }

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
            if(it<200) TagEntity(TagEntity.TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(it), it)
            else TagEntity(TagEntity.TAG_EMOTIONAL, EmotionalTag.findEmotionalStringTag(it), it)
        }
        holder.binding.rvMumentTag.adapter = MumentTagListAdapter()
        (holder.binding.rvMumentTag.adapter as MumentTagListAdapter).submitList(data)
        holder.binding.setVariable(BR.mument, getItem(position))

        holder.binding.root.setOnClickListener {
            getItem(position)._id?.let { it -> showDetailListener(it) }
        }

        holder.binding.ivLike.setOnClickListener {
            Timber.d("Test : ${getItem(position)._id}")
            holder.binding.ivLike.isSelected = true
        }
    }

    class MumentViewHolder(val binding: ItemLockerCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val like = binding.ivLike
        init {
            like.setOnClickListener {
            }
        }
    }
}