package com.mument_android.locker.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mument_android.locker.BR
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.*
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.locker.LikeMumentListener
import com.mument_android.locker.databinding.ItemLockerCardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//자식어뎁터
class LockerMumentLinearAdapter (
    private val isMumentTab: Boolean,
    private val showDetailListener: (String, MusicInfoEntity) -> Unit,
    private val likeMumentListener: LikeMumentListener
) : ListAdapter<LockerMumentEntity.MumentLockerCard, LockerMumentLinearAdapter.MumentViewHolder>(
    GlobalDiffCallBack<LockerMumentEntity.MumentLockerCard>()
) {

    var isOther = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MumentViewHolder {
        val binding = ItemLockerCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MumentViewHolder, position: Int) {
        val data = getItem(position).cardTag?.map {
            if (it < 200) TagEntity(
                TagEntity.TAG_IMPRESSIVE,
                ImpressiveTag.findImpressiveStringTag(it),
                it
            )
            else TagEntity(TagEntity.TAG_EMOTIONAL, EmotionalTag.findEmotionalStringTag(it), it)
        }
        holder.binding.rvMumentTag.adapter = MumentTagListAdapter()
        (holder.binding.rvMumentTag.adapter as MumentTagListAdapter).submitList(data)
        holder.binding.setVariable(BR.mument, getItem(position))
        holder.binding.setVariable(BR.isOther, isOther)

        likeMument(holder)
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

    private fun likeMument(holder: MumentViewHolder) {
        val mument = getItem(holder.absoluteAdapterPosition)
        holder.binding.run {
            laLikeLocker.setOnClickListener {
                if (tvLikeCount.text.myIsDigitsOnly()) {
                    val likeCount = tvLikeCount.text.toString().toInt()
                    if (laLikeLocker.progress == 0F) {
                        laLikeLocker.playAnimation()
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            likeMumentListener.likeMument(
                                mument._id ?: ""
                            )
                            tvLikeCount.text = (likeCount + 1).toString()
                        }
                    } else {
                        likeMumentListener.cancelLikeMument(mument._id ?: "")
                        laLikeLocker.progress = 0F
                        tvLikeCount.text = (likeCount - 1).toString()
                    }
                }
            }
            tvLikeCount.click {
                if (tvLikeCount.text.myIsDigitsOnly()) {
                    val likeCount = tvLikeCount.text.toString().toInt()
                    if (laLikeLocker.progress == 0F) {
                        laLikeLocker.playAnimation()
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            likeMumentListener.likeMument(
                                mument._id ?: ""
                            )
                            tvLikeCount.text = (likeCount + 1).toString()
                        }
                    } else {
                        likeMumentListener.cancelLikeMument(mument._id ?: "")
                        laLikeLocker.progress = 0F
                        tvLikeCount.text = (likeCount - 1).toString()
                    }
                }
            }
        }
    }

    class MumentViewHolder(val binding: ItemLockerCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}