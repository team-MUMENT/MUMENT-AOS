package com.mument_android.mypage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.mypage.data.NoticeData
import com.mument_android.mypage.databinding.ItemNoticeBinding

class NoticeAdapter :
    ListAdapter<NoticeData, NoticeAdapter.NoticeViewHolder>(GlobalDiffCallBack<NoticeData>()) {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(data: NoticeData)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    class NoticeViewHolder(private var binding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noticeData: NoticeData) {
            binding.tvNoticeItemTitle.text = noticeData.title
            binding.tvNoticeItemDate.text = noticeData.created_at
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val binding =
            ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(getItem(position))
        }
    }
}