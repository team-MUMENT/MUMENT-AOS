package com.startup.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.domain.entity.home.RecentSearchData
import com.startup.core_dependent.util.GlobalDiffCallBack
import com.startup.record.databinding.ItemBottomsheetSearchListBinding

class SearchListAdapter(
    private val contentClickListener: (RecentSearchData) -> Unit,
    private val itemClickListener: (RecentSearchData) -> Unit
) : ListAdapter<RecentSearchData, SearchListAdapter.SearchViewHolder>(GlobalDiffCallBack<RecentSearchData>()) {
    var option = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemBottomsheetSearchListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchData = getItem(position)
        holder.binding.setVariable(BR.bottomSearchData, searchData)
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

    class SearchViewHolder(val binding: ItemBottomsheetSearchListBinding): RecyclerView.ViewHolder(binding.root)
}