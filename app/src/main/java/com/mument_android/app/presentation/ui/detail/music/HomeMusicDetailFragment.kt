package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.presentation.ui.detail.mument.MoveMusicDetailNavigatorProvider
import com.mument_android.app.presentation.ui.detail.mument.MoveRecordProvider
import com.mument_android.app.presentation.ui.detail.mument.MumentClickListener
import com.mument_android.app.presentation.ui.detail.mument.MumentDetailFragment.Companion.FROM_HOME
import com.mument_android.app.presentation.ui.home.HomeFragmentDirections
import com.mument_android.app.presentation.ui.home.HomeFrameFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeMusicDetailFragment : BaseMusicDetailFragment() {
    private val args: HomeMusicDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setMumentListAdapter()
        musicDetailViewModel.musicId.observe(viewLifecycleOwner) {
            musicDetailViewModel.fetchMusicDetail(it)
            musicDetailViewModel.fetchMumentList(it)
        }
        musicDetailViewModel.changeMusicId(args.musicIdFromHome)
        moveToHistoryFragment()
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.setOnClickListener {
            val action =
                HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHistoryFragment(
                    args.musicIdFromHome
                )
            findNavController().navigate(action)
        }

        binding.cslEmptyView.setOnClickListener {
            musicDetailViewModel.myMument.value?.musicId?.let {
                val action =
                    HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHomeMumentDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun setMumentListAdapter() {
        setEveryMumentListAdapter(
            MusicDetailMumentListAdapter(object : MumentClickListener {
                override fun showMumentDetail(mumentId: String) {
                    Timber.e("$mumentId")
                    val action =
                        HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHomeMumentDetailFragment(
                            mumentId
                        )
                    findNavController().navigate(action)
                }

                override fun likeMument(mumentId: String) {
                    musicDetailViewModel.likeMument(mumentId)
                }

                override fun cancelLikeMument(mumentId: String) {
                    musicDetailViewModel.cancelLikeMument(mumentId)
                }
            })
        )
    }
}