package com.mument_android.app.data.network.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.SearchResultData
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemSearchListBinding

class SearchListAdapter(
    val contentClickListener: (SearchResultData) -> Unit,
    val itemClickListener: (SearchResultData) -> Unit
) :
    ListAdapter<SearchResultData, SearchListAdapter.SearchViewHolder>(GlobalDiffCallBack<SearchResultData>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), contentClickListener, itemClickListener
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchData = getItem(position)
        holder.binding.setVariable(BR.searchdata, searchData)
        holder.binding.clSearch.setOnClickListener {
            contentClickListener(searchData)
        }
        holder.binding.ivDelete.setOnClickListener {
            itemClickListener(searchData)
        }
    }

    class SearchViewHolder(
        val binding: ItemSearchListBinding,
        val contentClickListener: (SearchResultData) -> Unit,
        val itemClickListener: (SearchResultData) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root)
}