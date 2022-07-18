package com.mument_android.app.presentation.ui.detail.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
import com.mument_android.app.data.dto.MumentCard
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.MumentLayoutCardviewBinding

class SongDetailMumentListAdapter: ListAdapter<MumentCard, SongDetailMumentListAdapter.SongDetailMumentListViewHolder>(
    GlobalDiffCallBack<MumentCard>()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongDetailMumentListViewHolder {
        val binding = MumentLayoutCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongDetailMumentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongDetailMumentListViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.mument, getItem(position))
            setVariable(BR.isSongDetail, true)
        }
    }

    class SongDetailMumentListViewHolder(val binding: MumentLayoutCardviewBinding): RecyclerView.ViewHolder(binding.root)
}