package com.mument_android.app.presentation.ui.detail.mument

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mument_android.app.presentation.ui.detail.music.LockerMusicDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LockerMumentDetailFragment: MumentDetailFragment() {
    private val args: LockerMumentDetailFragmentArgs by navArgs()

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
            val action = LockerMumentDetailFragmentDirections.actionMumentDetailFragmentToLockerMusicDetailFragment(
                viewModel.mumentDetailContent.value?.data?.musicInfo?.id ?: ""
            )
            Timber.e(viewModel.mumentDetailContent.value?.data?.musicInfo?.id)
            findNavController().navigate(action)
        }
    }

    private fun goToHistory() {
        binding.tvGoToHistory.setOnClickListener {
            viewModel.mumentDetailContent.value?.data?.musicInfo?.id?.let {
                val action = LockerMumentDetailFragmentDirections.actionLockerMumentDetailFragmentToHistoryFragment(it)
                findNavController().navigate(action)
            }
        }
    }
}