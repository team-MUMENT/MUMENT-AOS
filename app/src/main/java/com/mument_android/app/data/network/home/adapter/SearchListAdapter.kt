package com.mument_android.app.data.network.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.SearchResultData
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.databinding.ItemSearchListBinding

class SearchListAdapter(
    private val context: Context,
    private val contentClickListener: (SearchResultData) -> Unit,
    private val itemClickListener: (SearchResultData) -> Unit
) :
    ListAdapter<SearchResultData, SearchListAdapter.SearchViewHolder>(GlobalDiffCallBack<SearchResultData>()) {

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
        with(holder.binding.clSearch.layoutParams as ViewGroup.MarginLayoutParams) {
            topMargin = if (position == 0) 30.dpToPx(context) else 0.dpToPx(context)
            holder.binding.clSearch.layoutParams = this
        }
    }

    class SearchViewHolder(
        val binding: ItemSearchListBinding,
    ) :
        RecyclerView.ViewHolder(binding.root)
}