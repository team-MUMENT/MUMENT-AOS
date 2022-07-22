package com.mument_android.app.presentation.ui.detail.mument

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMumentDetailFragment: MumentDetailFragment() {
    private val args: HomeMumentDetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.mumentId.isNotEmpty()) {
            viewModel.changeMumentId(args.mumentId)
            viewModel.fetchMumentDetailContent(args.mumentId)
        }

        goToHistory()
        goToMusicDetail()
    }

    private fun goToMusicDetail()  {
        binding.viewAlbumClickArea.setOnClickListener {
            viewModel.mumentDetailContent.value?.data?.musicInfo?.id?.let {
                val action = HomeMumentDetailFragmentDirections.actionHomeMumentDetailFragmentToHomeMusicDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun goToHistory() {
        binding.tvGoToHistory.setOnClickListener {
            viewModel.mumentDetailContent.value?.data?.musicInfo?.id?.let {
                val action = HomeMumentDetailFragmentDirections.actionHomeMumentDetailFragmentToHistoryFragment(it)
                findNavController().navigate(action)
            }
        }
    }
}