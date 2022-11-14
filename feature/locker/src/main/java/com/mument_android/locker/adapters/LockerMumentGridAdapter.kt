package com.mument_android.locker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mument_android.locker.BR
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.locker.databinding.ItemMumentImageBinding

//자식어뎁터
class LockerMumentGridAdapter(
    private val showDetailListener: (String) -> Unit
) :
    ListAdapter<LockerMumentEntity.MumentLockerCard, LockerMumentGridAdapter.MumentViewHolder>(
        GlobalDiffCallBack<LockerMumentEntity.MumentLockerCard>()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MumentViewHolder {
        val binding = ItemMumentImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MumentViewHolder, position: Int) {
        holder.binding.setVariable(BR.mument, getItem(position))
        holder.binding.root.setOnClickListener {
            getItem(position)._id?.let { name -> showDetailListener(name) }
        }
    }

    class MumentViewHolder(val binding: ItemMumentImageBinding) : RecyclerView.ViewHolder(binding.root)

}
