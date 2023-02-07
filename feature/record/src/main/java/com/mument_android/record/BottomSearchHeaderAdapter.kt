package com.mument_android.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.record.databinding.ItemBottomSearchHeaderBinding

class BottomSearchHeaderAdapter :
    RecyclerView.Adapter<BottomSearchHeaderAdapter.SearchHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHeaderViewHolder =
        SearchHeaderViewHolder(
            ItemBottomSearchHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchHeaderViewHolder, position: Int) {}
    override fun getItemCount(): Int = 1
    class SearchHeaderViewHolder(val binding: ItemBottomSearchHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)
}
