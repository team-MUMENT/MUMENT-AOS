package com.mument_android.locker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.locker.BR
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.locker.LikeMumentListener
import com.mument_android.locker.databinding.ItemLockerDateBinding

//부모 어뎁터
class LockerTimeAdapter(
    private val isGridLayout: Boolean,
    private val showDetailListener: (String, MusicInfoEntity) -> Unit,
    private val likeMumentListener: LikeMumentListener
): ListAdapter<LockerMumentEntity, LockerTimeAdapter.LockerTimeViewHolder>(
    GlobalDiffCallBack<LockerMumentEntity>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LockerTimeViewHolder {
        val binding = ItemLockerDateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LockerTimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerTimeViewHolder, position: Int) {
        holder.binding.setVariable(BR.lockerMumentEntity, getItem(position))
        setMumentList(holder)
    }

    private fun setMumentList(holder: LockerTimeViewHolder) {
        val mumentList = getItem(holder.absoluteAdapterPosition)
        holder.binding.rvMumentLinear.run {
            if(isGridLayout) {
                adapter = LockerMumentGridAdapter { mumentId, musicInfo ->
                    showDetailListener(mumentId, musicInfo)
                }
                (adapter as LockerMumentGridAdapter).submitList(mumentList.mumentCard)
                val gridLayoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
                holder.binding.rvMumentLinear.layoutManager = gridLayoutManager
            } else {
                adapter = LockerMumentLinearAdapter(
                    showDetailListener = { mumentId, musicInfo ->
                        showDetailListener(mumentId, musicInfo)
                                         },
                    likeMumentListener = object: LikeMumentListener {
                        override fun likeMument(mumetId: String) {
                            likeMumentListener.likeMument(mumetId)
                        }
                        override fun cancelLikeMument(mumetId: String) {
                            likeMumentListener.cancelLikeMument(mumetId)
                        }
                    }
                )

                (adapter as LockerMumentLinearAdapter).submitList(mumentList.mumentCard)
            }
        }
    }

    class LockerTimeViewHolder(val binding: ItemLockerDateBinding) : RecyclerView.ViewHolder(binding.root)
}