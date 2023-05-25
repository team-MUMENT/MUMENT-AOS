package com.mument_android.locker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.*
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.GlobalDiffCallBack
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.core_dependent.util.myIsDigitsOnly
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.locker.BR
import com.mument_android.core_dependent.util.LikeMumentListener
import com.mument_android.locker.databinding.ItemLockerCardBinding
import kotlinx.coroutines.*

//자식어뎁터
class LockerMumentLinearAdapter(
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
            if (isMumentTab) {
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
            llLikeTouchArea.setOnClickListener {
                if (tvLikeCount.text.myIsDigitsOnly()) {
                    llLikeTouchArea.isClickable = false
                    val likeCount = tvLikeCount.text.toString().toInt()
                    if (laLikeLocker.progress == 0F) {
                        laLikeLocker.playAnimation()
                        val job = CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            tvLikeCount.text = (likeCount + 1).toString()
                            llLikeTouchArea.isClickable = true
                        }
                        likeMumentListener.likeMument(
                            mument._id ?: ""
                        ) { result ->
                            if (!result) {
                                job.cancel()
                                laLikeLocker.progress = 0F
                                llLikeTouchArea.isClickable = true
                                tvLikeCount.text = likeCount.toString()
                                holder.binding.root.context.showToast("오류가 발생했습니다.")
                            }
                        }
                    } else {
                        laLikeLocker.progress = 0F
                        tvLikeCount.text = (likeCount - 1).toString()
                        likeMumentListener.cancelLikeMument(mument._id ?: "") { result ->
                            if (!result) {
                                laLikeLocker.progress = 100F
                                tvLikeCount.text = likeCount.toString()
                                holder.binding.root.context.showToast("오류가 발생했습니다.")
                            }
                            llLikeTouchArea.isClickable = true
                        }
                    }
                }
            }
        }
    }

    class MumentViewHolder(val binding: ItemLockerCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}