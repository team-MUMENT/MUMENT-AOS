package com.startup.locker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.locker.BR
import com.startup.domain.entity.locker.LockerMumentEntity
import com.startup.core_dependent.util.GlobalDiffCallBack
import com.startup.locker.LikeMumentListener
import com.startup.locker.databinding.ItemLockerDateBinding

//부모 어뎁터
class LockerTimeAdapter(
    private val isGridLayout: Boolean,
    private val showDetailListener: (String) -> Unit,
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
                adapter = LockerMumentGridAdapter {
                    showDetailListener(it)
                }
                (adapter as LockerMumentGridAdapter).submitList(mumentList.mumentCard)
                val gridLayoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
                holder.binding.rvMumentLinear.layoutManager = gridLayoutManager
            } else {
                adapter = LockerMumentLinearAdapter(
                    showDetailListener={ showDetailListener(it)

                }, object: LikeMumentListener {
                    override fun likeMument(mumetId: String) {
                        likeMumentListener.likeMument(mumetId)
                    }

                    override fun cancelLikeMument(mumetId: String) {
                        likeMumentListener.cancelLikeMument(mumetId)
                    }
                })

                (adapter as LockerMumentLinearAdapter).submitList(mumentList.mumentCard)
            }
        }
    }

    class LockerTimeViewHolder(val binding: ItemLockerDateBinding) : RecyclerView.ViewHolder(binding.root)
}