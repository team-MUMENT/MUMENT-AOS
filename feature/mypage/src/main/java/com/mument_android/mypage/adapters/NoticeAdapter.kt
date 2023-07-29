package com.mument_android.mypage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.domain.entity.mypage.NoticeListEntity
import com.mument_android.mypage.BR
import com.mument_android.mypage.databinding.ItemNoticeBinding

class NoticeAdapter(val clickListener:(NoticeListEntity)->Unit) :
    ListAdapter<NoticeListEntity, NoticeAdapter.NoticeViewHolder>(GlobalDiffCallBack<NoticeListEntity>()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val binding =
            ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.setVariable(BR.noticeListEntity, data)
        holder.binding.root.setOnClickListener {
            clickListener(data)
        }
    }

    class NoticeViewHolder(val binding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root)
}