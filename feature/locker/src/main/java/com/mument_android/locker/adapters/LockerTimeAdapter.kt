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
import com.mument_android.core_dependent.util.LikeMumentListener
import com.mument_android.locker.databinding.ItemLockerDateBinding

//부모 어뎁터
class LockerTimeAdapter(
    private val isMumentTab : Boolean,
    private val isGridLayout: Boolean,
    private val showDetailListener: (String, MusicInfoEntity) -> Unit,
    private val likeMumentListener: LikeMumentListener
) : ListAdapter<LockerMumentEntity, LockerTimeAdapter.LockerTimeViewHolder>(
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
            if (isGridLayout) {
                adapter = LockerMumentGridAdapter(isMumentTab = isMumentTab) { mumentId, musicInfo ->

                    showDetailListener(mumentId, musicInfo)
                }.apply {
                    submitList(mumentList.mumentCard)
                }
                holder.binding.rvMumentLinear.layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
            } else {
                adapter = LockerMumentLinearAdapter(
                    isMumentTab = isMumentTab,
                    showDetailListener = { mumentId, musicInfo ->
                        showDetailListener(mumentId, musicInfo)
                    },
                    likeMumentListener = object : LikeMumentListener {
                        override fun likeMument(mumetId: String, resultCallback: (Boolean) -> Unit) {
                            likeMumentListener.likeMument(mumetId, resultCallback)
                        }

                        override fun cancelLikeMument(mumetId: String, resultCallback: (Boolean) -> Unit) {
                            likeMumentListener.cancelLikeMument(mumetId, resultCallback)
                        }
                    }
                ).apply {
                    submitList(mumentList.mumentCard)
                    if (mumentList.isOther == true) {
                        isOther = true
                    }
                }
            }
        }
    }

    class LockerTimeViewHolder(val binding: ItemLockerDateBinding) :
        RecyclerView.ViewHolder(binding.root)
}