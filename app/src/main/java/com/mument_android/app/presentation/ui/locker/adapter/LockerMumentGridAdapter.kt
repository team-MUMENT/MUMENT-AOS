package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.domain.entity.locker.LockerMumentEntity
import com.mument_android.databinding.ItemMumentImageBinding
import com.startup.core_dependent.util.GlobalDiffCallBack

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
