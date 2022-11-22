package com.mument_android.home.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.home.adapters.SearchListAdapter


@BindingAdapter("app:ui_state_search_result")
fun RecyclerView.bindUiStateSearchResultList(uiState: UiState){
    val boundAdapter = this.adapter
    visibility = if (boundAdapter is SearchListAdapter && uiState is UiState.Success<*>) {
        (uiState.data as List<RecentSearchData>).run {
            boundAdapter.submitList(this)
        }
        View.VISIBLE
    } else {
        View.GONE
    }
}


