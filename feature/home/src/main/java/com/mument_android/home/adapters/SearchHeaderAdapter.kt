package com.mument_android.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mument_android.home.databinding.ItemSearchHeaderBinding

class SearchHeaderAdapter(private val onClickAllDelete: () -> Unit) :
    RecyclerView.Adapter<SearchHeaderAdapter.SearchHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHeaderViewHolder =
        SearchHeaderViewHolder(
            ItemSearchHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SearchHeaderViewHolder, position: Int) {
        holder.binding.etAllDelete.setOnClickListener { onClickAllDelete() }
    }

    override fun getItemCount(): Int = 1
    class SearchHeaderViewHolder(val binding: ItemSearchHeaderBinding) :
        ViewHolder(binding.root)
}