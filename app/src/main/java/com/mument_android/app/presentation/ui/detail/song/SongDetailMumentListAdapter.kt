package com.mument_android.app.presentation.ui.detail.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.BR
<<<<<<< HEAD
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.ItemSongDetailMumentBinding

class SongDetailMumentListAdapter: ListAdapter<MusicDetailEntity, SongDetailMumentListAdapter.SongDetailMumentListViewHolder>(
    GlobalDiffCallBack<MusicDetailEntity>()
=======
import com.mument_android.app.data.dto.MumentCard
import com.mument_android.app.util.GlobalDiffCallBack
import com.mument_android.databinding.MumentLayoutCardviewBinding

class SongDetailMumentListAdapter: ListAdapter<MumentCard, SongDetailMumentListAdapter.SongDetailMumentListViewHolder>(
    GlobalDiffCallBack<MumentCard>()
>>>>>>> ee996df ([FEAT] #54 - 곡 상세보기 UI 작업)
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongDetailMumentListViewHolder {
<<<<<<< HEAD
        val binding = ItemSongDetailMumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
=======
        val binding = MumentLayoutCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
>>>>>>> ee996df ([FEAT] #54 - 곡 상세보기 UI 작업)
        return SongDetailMumentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongDetailMumentListViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.mument, getItem(position))
<<<<<<< HEAD
        }
    }

    class SongDetailMumentListViewHolder(val binding: ItemSongDetailMumentBinding): RecyclerView.ViewHolder(binding.root)
=======
            setVariable(BR.isSongDetail, true)
        }
    }

    class SongDetailMumentListViewHolder(val binding: MumentLayoutCardviewBinding): RecyclerView.ViewHolder(binding.root)
>>>>>>> ee996df ([FEAT] #54 - 곡 상세보기 UI 작업)
}