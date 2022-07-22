package com.mument_android.app.presentation.ui.detail.mument

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mument_android.app.presentation.ui.detail.music.HomeMusicDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
            val action = HomeMumentDetailFragmentDirections.actionHomeMumentDetailFragmentToHomeMusicDetailFragment(
                viewModel.mumentDetailContent.value?.data?.musicInfo?.id ?: ""
            )
            Timber.e(viewModel.mumentDetailContent.value?.data?.musicInfo?.id)
            findNavController().navigate(action)
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