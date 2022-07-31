package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.presentation.ui.detail.mument.navigator.MoveRecordProvider
import com.mument_android.app.presentation.ui.detail.mument.MumentClickListener
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
        musicDetailViewModel.changeMusicId(args.musicId)
        moveToHistoryFragment()
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.setOnClickListener {
            val action = HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHistoryFragment(args.musicId)
            findNavController().navigate(action)
        }

        binding.layoutMyMument.root.setOnClickListener {
            musicDetailViewModel.myMument.value?.let {
                val action = HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHistoryFragment(it.mumentId)
                findNavController().navigate(action)
            }
        }
    }

    private fun setMumentListAdapter() {
        setEveryMumentListAdapter(
            MusicDetailMumentListAdapter(object : MumentClickListener {
                override fun showMumentDetail(mumentId: String) {
                    val action = HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToMumentDetailFragment(mumentId)
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