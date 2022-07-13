package com.mument_android.app.presentation.ui.locker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.TestLockerMumentCard
import com.mument_android.databinding.ItemLockerDateBinding
import timber.log.Timber

//부모 어뎁터
//TODO : 월별 리스트 모아보기
class LockerTimeAdapter() : RecyclerView.Adapter<LockerTimeAdapter.LockerTimeViewHolder>() {
    var data = listOf<MumentCard>()
    //var mument = listOf<MumentCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LockerTimeViewHolder {
        val binding = ItemLockerDateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LockerTimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerTimeViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class LockerTimeViewHolder(
        val binding: ItemLockerDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val lockerMumentAdapter = LockerMumentAdapter()
        fun onBind(data: MumentCard) {
            binding.apply {
                rvMumentLinear.adapter = lockerMumentAdapter
                mumentDate = data
                Timber.d("TestAdapter : ${listOf(data)}")
                lockerMumentAdapter.setMument(listOf(data))
                executePendingBindings()
            }
        }
    }

    fun setTime(data: MutableList<MumentCard>) {
        this.data = data
        notifyDataSetChanged()
    }

}