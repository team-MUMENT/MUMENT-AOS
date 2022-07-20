package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.LockerMumentEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemLockerDateBinding
import timber.log.Timber

//부모 어뎁터
class LockerTimeAdapter(private val isGridLayout: Boolean): ListAdapter<LockerMumentEntity,LockerTimeAdapter.LockerTimeViewHolder>(
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
                adapter = LockerMumentGridAdapter()
                (adapter as LockerMumentGridAdapter).submitList(mumentList.mumentCard)
                val gridLayoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
                holder.binding.rvMumentLinear.layoutManager = gridLayoutManager
            } else {
                adapter = LockerMumentLinearAdapter()
                (adapter as LockerMumentLinearAdapter).submitList(mumentList.mumentCard)
            }
        }
    }

    class LockerTimeViewHolder(val binding: ItemLockerDateBinding) : RecyclerView.ViewHolder(binding.root)
}