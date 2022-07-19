package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.app.domain.entity.LockerMumentEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemMumentImageBinding

//자식어뎁터
class LockerMumentGridAdapter() :
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
        //TODO: 클릭 리스너로 상세 뮤멘트 연결
    }

    class MumentViewHolder(val binding: ItemMumentImageBinding) : RecyclerView.ViewHolder(binding.root)

}
