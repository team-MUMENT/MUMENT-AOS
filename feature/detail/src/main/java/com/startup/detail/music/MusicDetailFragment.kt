package com.startup.detail.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.startup.core_dependent.ext.collectFlowWhenStarted
import com.startup.core_dependent.ext.click
import com.startup.core_dependent.util.AutoClearedValue
import com.startup.core_dependent.util.RecyclerviewItemDivider
import com.startup.core_dependent.util.RecyclerviewItemDivider.Companion.IS_VERTICAL
import com.startup.core_dependent.util.ViewUtils.dpToPx
import com.startup.detail.databinding.FragmentBaseMusicDetailBinding
import com.startup.detail.mument.MumentClickListener
import com.startup.core_dependent.ui.MumentTagListAdapter
import com.startup.detail.viewmodels.MusicDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicDetailFragment(): Fragment() {
    private var binding by AutoClearedValue<FragmentBaseMusicDetailBinding>()
    private val musicDetailViewModel: MusicDetailViewModel by viewModels()
    //@Inject lateinit var recordProvider: MoveRecordProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBaseMusicDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicDetailViewModel = musicDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.ivBack.click { findNavController().popBackStack() }

        recordMument()
        fetchMusicDetailContents()
        changeMumentSort()
        updateEveryMuments()
        setMyMumentTagList()
        setEntireMumentListAdapter()
        moveToHistoryFragment()
    }

    private fun recordMument() {
        binding.tvRecordMument.click {
            musicDetailViewModel.musicInfo.value?.let { musicInfo ->
                //recordProvider.recordMusic(Music(musicInfo.id, musicInfo.name, musicInfo.artist, musicInfo.thumbnail))
            }
        }
    }

    private fun fetchMusicDetailContents() {
        musicDetailViewModel.musicId.observe(viewLifecycleOwner) {
            musicDetailViewModel.fetchMusicDetail(it)
            musicDetailViewModel.fetchMumentList(it)
        }
        arguments?.getString(MUSIC_ID)?.let { musicDetailViewModel.changeMusicId(it) }
    }

    private fun changeMumentSort() {
        binding.tvSortLikeCount.click { musicDetailViewModel.changeSelectedSort(binding.tvSortLikeCount.text.toString()) }
        binding.tvSortLatest.click { musicDetailViewModel.changeSelectedSort(binding.tvSortLatest.text.toString()) }
        collectFlowWhenStarted(musicDetailViewModel.selectedSort) {
            binding.tvSortLatest.changeSelectedSortTheme(it)
            binding.tvSortLikeCount.changeSelectedSortTheme(it)
        }
    }

    private fun setEntireMumentListAdapter() {
        binding.rvEveryMuments.run {
            addItemDecoration(RecyclerviewItemDivider(0, 15.dpToPx(requireContext()), IS_VERTICAL))
            adapter = MusicDetailMumentListAdapter(object : MumentClickListener {
                override fun showMumentDetail(mumentId: String) {
                    /*Bundle().also {
                        it.putString(MumentDetailFragment.MUMENT_ID, mumentId)
                        findNavController().navigate(R.id.action_homeMusicDetailFragment_to_mumentDetailFragment, it)
                    } Todo Navi*/
                }

                override fun likeMument(mumentId: String) {
                    musicDetailViewModel.likeMument(mumentId)
                }

                override fun cancelLikeMument(mumentId: String) {
                    musicDetailViewModel.cancelLikeMument(mumentId)
                }
            })
        }
    }

    private fun updateEveryMuments() {
        collectFlowWhenStarted(musicDetailViewModel.mumentList) {
            (binding.rvEveryMuments.adapter as MusicDetailMumentListAdapter).submitList(it)
        }
    }

    private fun setMyMumentTagList() {
        binding.layoutMyMument.rvMumentTags.adapter = MumentTagListAdapter()
        collectFlowWhenStarted(musicDetailViewModel.myMument) { mumentSummary ->
            (binding.layoutMyMument.rvMumentTags.adapter as MumentTagListAdapter).submitList(musicDetailViewModel.mapTagList())
        }
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.click {
            /*Bundle().also {
                it.putString(MUSIC_ID, musicDetailViewModel.musicId.value ?: "")
                findNavController().navigate(R.id.action_musicDetailFragment_to_historyFragment, it)
            } Todo Navi*/
        }

        binding.layoutMyMument.root.setOnClickListener {
            musicDetailViewModel.myMument.value?.let {
                /*Bundle().also {
                    it.putString(MUSIC_ID, musicDetailViewModel.musicId.value ?: "")
                    findNavController().navigate(R.id.action_musicDetailFragment_to_historyFragment, it)
                } Todo Navi*/
            }
        }
    }

    private fun AppCompatTextView.changeSelectedSortTheme(selectedSort: String) {
        isSelected = selectedSort == text.toString()
    }

    companion object {
        const val MUSIC_ID = "MUSIC_ID"
    }
}