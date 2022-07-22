package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMusicDetailFragment: BaseMusicDetailFragment() {
    private val args: HomeMusicDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicDetailViewModel.musicId.observe(viewLifecycleOwner) {
            musicDetailViewModel.fetchMusicDetail(it)
            musicDetailViewModel.fetchMumentList(it)
        }
        musicDetailViewModel.changeMusicId(args.musicIdFromHome)
        moveToHistoryFragment()
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.setOnClickListener {
            val action = HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToMumentHistoryFragment(args.musicIdFromHome)
            findNavController().navigate(action)
        }
    }
}