package com.mument_android.app.data.network.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemSearchListBinding

class SearchListAdapter(
    private val contentClickListener: (RecentSearchData) -> Unit,
    private val itemClickListener: (RecentSearchData) -> Unit
) : ListAdapter<RecentSearchData, SearchListAdapter.SearchViewHolder>(GlobalDiffCallBack<RecentSearchData>()) {
    var option = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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
        if(option){
            holder.binding.ivDelete.visibility = View.VISIBLE
        }else{
            holder.binding.ivDelete.visibility = View.GONE
        }
    }

    class SearchViewHolder(val binding: ItemSearchListBinding): RecyclerView.ViewHolder(binding.root)
}