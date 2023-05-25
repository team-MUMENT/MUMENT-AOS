package com.mument_android.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.core_dependent.util.myIsDigitsOnly
import com.mument_android.detail.BR
import com.mument_android.detail.databinding.ItemMusicDetailMumentBinding
import com.mument_android.detail.mument.listener.MumentClickListener
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicDetailMumentHeaderAdapter(
    private val onClickHistory: () -> Unit, private val mumentClickListener: MumentClickListener
) : RecyclerView.Adapter<MusicDetailMumentHeaderAdapter.MusicDetailHeaderViewHolder>() {
    var myMumentInfo: MumentSummaryEntity? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MusicDetailHeaderViewHolder(
        ItemMusicDetailMumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MusicDetailHeaderViewHolder, position: Int) {

        holder.binding.setVariable(BR.mument, myMumentInfo)
        holder.binding.tvShowMyHistory.click {
            onClickHistory()
            //뮤멘트 히스토리 페이지에 진입 경로
            FirebaseAnalyticsUtil.firebaseLog(
                "mument_history_view",
                "journey",
                "from_song_detail"
            )
        }

        if (myMumentInfo != null) {
            holder.binding.clRoot.click {
                mumentClickListener.showMumentDetail(mumentId = myMumentInfo!!.mumentId)
                //뮤멘트 상세보기에 진입했을 때 GA
                FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_song_detail_page")

            }
            checkLikeMument(holder)
            setMumentTagList(holder)
        }
    }

    private fun checkLikeMument(holder: MusicDetailHeaderViewHolder) {
        holder.binding.run {
            llTouchArea.click {
                if (tvSecretLikecount.text.myIsDigitsOnly() && myMumentInfo != null) {
                    val likeCount = tvSecretLikecount.text.toString().toInt()
                    llTouchArea.isClickable = false
                    if (laLikeMumentDetail.progress == 0F) {
                        laLikeMumentDetail.playAnimation()
                        val job = CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            myMumentInfo!!.isLiked = true
                            tvSecretLikecount.text = (likeCount + 1).toString()
                            laLikeMumentDetail.progress = 100F
                            llTouchArea.isClickable = true
                        }
                        mumentClickListener.likeMument(
                            myMumentInfo!!.mumentId
                        ) { result ->
                            if (!result) {
                                job.cancel()
                                holder.binding.root.context.showToast("오류가 발생했습니다.")
                                llTouchArea.isClickable = true
                                myMumentInfo!!.isLiked = false
                                laLikeMumentDetail.progress = 0F
                                tvSecretLikecount.text = likeCount.toString()
                            }
                        }
                    } else {
                        myMumentInfo!!.isLiked = false
                        laLikeMumentDetail.progress = 0F
                        tvSecretLikecount.text = (likeCount - 1).toString()
                        mumentClickListener.cancelLikeMument(myMumentInfo!!.mumentId) { result ->
                            if (!result) {
                                myMumentInfo!!.isLiked = true
                                laLikeMumentDetail.progress = 100F
                                tvSecretLikecount.text = likeCount.toString()
                                holder.binding.root.context.showToast("오류가 발생했습니다.")
                            }
                            llTouchArea.isClickable = true
                        }
                    }
                }
            }
        }
    }

    private fun setMumentTagList(holder: MusicDetailHeaderViewHolder) {
        val mument = myMumentInfo
        holder.binding.rvMumentTags.run {
            adapter = MumentTagListAdapter()
            (adapter as MumentTagListAdapter).submitList(mument?.tags)
        }
    }

    override fun getItemCount(): Int = 1

    class MusicDetailHeaderViewHolder(val binding: ItemMusicDetailMumentBinding) :
        ViewHolder(binding.root)
}