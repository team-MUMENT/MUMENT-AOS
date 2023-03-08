package com.mument_android.locker.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mument_android.locker.BR
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.locker.databinding.ItemMumentImageBinding

//자식어뎁터
class LockerMumentGridAdapter(
    private val isMumentTab : Boolean,
    private val showDetailListener: (String, MusicInfoEntity) -> Unit
) :
    ListAdapter<LockerMumentEntity.MumentLockerCard, LockerMumentGridAdapter.MumentViewHolder>(
        GlobalDiffCallBack<LockerMumentEntity.MumentLockerCard>()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MumentViewHolder {
        val binding = ItemMumentImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MumentViewHolder, position: Int) {
        holder.binding.setVariable(BR.mument, getItem(position))
        holder.binding.root.setOnClickListener {
            //뮤멘트 상세보기에 진입했을 때 GA
            if(isMumentTab) {
                FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_storage_my_mument")
            } else {
                FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_storage_like_mument")
            }
            getItem(position)?.let { data ->
                showDetailListener(
                    data._id ?: "",
                    MusicInfoEntity(
                        id = data.music_Id ?: "",
                        name = data.musicName ?: "",
                        thumbnail = data.musicImage ?: "",
                        artist = data.musicArtist ?: ""
                    )
                )
            }
        }
    }

    class MumentViewHolder(val binding: ItemMumentImageBinding) : RecyclerView.ViewHolder(binding.root)

}
