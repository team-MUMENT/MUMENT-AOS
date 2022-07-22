package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mument_android.app.presentation.ui.detail.mument.MumentClickListener
import com.mument_android.app.presentation.ui.detail.mument.MumentDetailFragment.Companion.FROM_HOME
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeMusicDetailFragment: BaseMusicDetailFragment() {
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
            val action = HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHomeMumentDetailFragment(args.musicIdFromHome)
            findNavController().navigate(action)
        }
    }

    private fun setMumentListAdapter() {
        setEveryMumentListAdapter(
            MusicDetailMumentListAdapter(object: MumentClickListener {
                override fun showMumentDetail(mumentId: String) {
                    Timber.e("$mumentId")
                    val action = HomeMusicDetailFragmentDirections.actionHomeMusicDetailFragmentToHomeMumentDetailFragment(mumentId)
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