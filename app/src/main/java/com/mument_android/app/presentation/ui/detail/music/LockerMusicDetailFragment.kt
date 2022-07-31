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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMumentListAdapter()
        musicDetailViewModel.musicId.observe(viewLifecycleOwner) {
            musicDetailViewModel.fetchMusicDetail(it)
            musicDetailViewModel.fetchMumentList(it)
        }
        arguments?.getString("MUSIC_ID")?.let { musicDetailViewModel.changeMusicId(it) }

//        musicDetailViewModel.changeMusicId(args.musicIdFromLocker)
        moveToHistoryFragment()
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.setOnClickListener {
            val action = LockerMusicDetailFragmentDirections.actionLockerMusicDetailFragmentToHistoryFragment(arguments?.getString("MUSIC_ID") ?: "")
            findNavController().navigate(action)
        }
        binding.layoutMyMument.root.setOnClickListener {
            musicDetailViewModel.myMument.value?.let {
                val action = LockerMusicDetailFragmentDirections.actionLockerMusicDetailFragmentToMumentDetailFragment(it.mumentId)
                findNavController().navigate(action)
            }
        }
    }

    private fun setMumentListAdapter() {
        setEveryMumentListAdapter(MusicDetailMumentListAdapter(object: MumentClickListener {
            override fun showMumentDetail(mumentId: String) {
                val action = LockerMusicDetailFragmentDirections.actionLockerMusicDetailFragmentToMumentDetailFragment(mumentId)
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