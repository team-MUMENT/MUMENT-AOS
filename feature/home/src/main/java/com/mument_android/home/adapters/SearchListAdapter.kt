package com.mument_android.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.home.BR
import com.mument_android.home.databinding.ItemSearchListBinding

class SearchListAdapter(
    private val contentClickListener: (RecentSearchData) -> Unit,
    private val itemClickListener: (RecentSearchData) -> Unit
) : ListAdapter<RecentSearchData, SearchListAdapter.SearchViewHolder>(SearchDiffUtil) {
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
        if (searchData.createAt != null) {
            holder.binding.ivDelete.visibility = View.VISIBLE
        } else {
            holder.binding.ivDelete.visibility = View.GONE
        }
    }

    class SearchViewHolder(val binding: ItemSearchListBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object SearchDiffUtil : DiffUtil.ItemCallback<RecentSearchData>() {
        override fun areItemsTheSame(
            oldItem: RecentSearchData,
            newItem: RecentSearchData
        ): Boolean = oldItem._id == newItem._id

        override fun areContentsTheSame(
            oldItem: RecentSearchData,
            newItem: RecentSearchData
        ): Boolean = oldItem == newItem
    }
}