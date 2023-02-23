package com.mument_android.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.ext.click
import com.mument_android.detail.databinding.ItemMusicDetailListHeaderBinding

class MusicDetailListHeaderAdapter(private val onClickChangeSort: (MusicDetailContract.MusicDetailEvent) -> Unit) :
    RecyclerView.Adapter<MusicDetailListHeaderAdapter.MusicDetailListHeaderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MusicDetailListHeaderViewHolder(
        ItemMusicDetailListHeaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MusicDetailListHeaderViewHolder, position: Int) {
        holder.binding.run {
            tvSortLikeCount.changeSelectedSortTheme("좋아요순")
            tvSortLatest.click {
                onClickChangeSort(MusicDetailContract.MusicDetailEvent.ClickSortByLatest)
                tvSortLatest.changeSelectedSortTheme("최신순")
                tvSortLikeCount.changeSelectedSortTheme("최신순")
            }
            tvSortLikeCount.click {
                onClickChangeSort(MusicDetailContract.MusicDetailEvent.ClickSortByLikeCount)
                tvSortLikeCount.changeSelectedSortTheme("좋아요순")
                tvSortLatest.changeSelectedSortTheme("좋아요순")
            }
        }
    }

    override fun getItemCount(): Int = 1

    private fun AppCompatTextView.changeSelectedSortTheme(selectedSort: String) {
        isSelected = selectedSort == text.toString()
    }

    class MusicDetailListHeaderViewHolder(val binding: ItemMusicDetailListHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)
}