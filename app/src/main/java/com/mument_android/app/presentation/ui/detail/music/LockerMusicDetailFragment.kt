package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mument_android.app.presentation.ui.detail.mument.MumentClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockerMusicDetailFragment: BaseMusicDetailFragment() {
    private val args: LockerMusicDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMumentListAdapter()
        musicDetailViewModel.musicId.observe(viewLifecycleOwner) {
            musicDetailViewModel.fetchMusicDetail(it)
            musicDetailViewModel.fetchMumentList(it)
        }
        musicDetailViewModel.changeMusicId(args.musicIdFromLocker)
        moveToHistoryFragment()
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.setOnClickListener {
            val action = LockerMusicDetailFragmentDirections.actionLockerMusicDetailFragmentToHistoryFragment(args.musicIdFromLocker)
            findNavController().navigate(action)
        }
        binding.layoutMyMument.root.setOnClickListener {
            musicDetailViewModel.myMument.value?.let {
                val action =
                    HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHomeMumentDetailFragment(it.mumentId)
                findNavController().navigate(action)
            }
        }
    }

    private fun setMumentListAdapter() {
        setEveryMumentListAdapter(MusicDetailMumentListAdapter(object: MumentClickListener {
            override fun showMumentDetail(mumentId: String) {
                val action = LockerMusicDetailFragmentDirections.actionLockerMusicDetailFragmentToLockerMumentDetailFragment(mumentId)
                findNavController().navigate(action)
            }

            override fun likeMument(mumentId: String) {
                musicDetailViewModel.likeMument(mumentId)
            }

            override fun cancelLikeMument(mumentId: String) {
                musicDetailViewModel.cancelLikeMument(mumentId)
            }
        }))
    }
}