package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockerMusicDetailFragment: BaseMusicDetailFragment() {
    private val args: LockerMusicDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicDetailViewModel.musicId.observe(viewLifecycleOwner) {
            musicDetailViewModel.fetchMusicDetail(it)
            musicDetailViewModel.fetchMumentList(it)
        }
        musicDetailViewModel.changeMusicId(args.musicIdFromLocker)
        moveToHistoryFragment()
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.setOnClickListener {
            val action = LockerMusicDetailFragmentDirections.actionLockerMusicDetailFragmentToHistoryFragment3(args.musicIdFromLocker)
            findNavController().navigate(action)
        }
    }
}