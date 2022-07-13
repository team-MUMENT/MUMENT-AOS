package com.mument_android.app.presentation.ui.locker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.app.domain.entity.TestLockerMumentCard
import com.mument_android.databinding.ItemLockerDateBinding

//자식어뎁터
class LockerMumentAdapter : RecyclerView.Adapter<LockerMumentAdapter.MumentViewHolder>() {

    var mumentData = mutableListOf<TestLockerMumentCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MumentViewHolder {
        val binding = ItemLockerDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MumentViewHolder, position: Int) {
        holder.onBind(mumentData[position])
        //TODO: 클릭 리스너로 상세 뮤멘트 연결
    }


    override fun getItemCount(): Int = mumentData.size

    inner class MumentViewHolder(
        val binding: ItemLockerDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data : TestLockerMumentCard) {

            binding.apply {
                this.mumentDate = data

                //lockerTimeAdapter.setTime()
                executePendingBindings()
            }
        }
    }

    fun setMument(data: MutableList<TestLockerMumentCard>) {
        this.mumentData = data
        notifyDataSetChanged()
    }


}
