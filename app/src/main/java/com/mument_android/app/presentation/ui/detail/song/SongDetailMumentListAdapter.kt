package com.mument_android.app.presentation.ui.detail.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemSongDetailMumentBinding


class SongDetailMumentListAdapter: ListAdapter<MusicDetailEntity, SongDetailMumentListAdapter.SongDetailMumentListViewHolder>(
    GlobalDiffCallBack<MusicDetailEntity>()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongDetailMumentListViewHolder {
        val binding = ItemSongDetailMumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongDetailMumentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongDetailMumentListViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.mument, getItem(position))
        }
    }
    class SongDetailMumentListViewHolder(val binding: ItemSongDetailMumentBinding): RecyclerView.ViewHolder(binding.root)
}