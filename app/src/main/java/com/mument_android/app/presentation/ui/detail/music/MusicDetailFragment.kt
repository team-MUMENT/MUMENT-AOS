package com.mument_android.app.presentation.ui.detail.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_VERTICAL
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMusicDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MusicDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMusicDetailBinding>()
    private val musicDetailViewModel: MusicDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMusicDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicDetailViewModel = musicDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setEveryMumentListAdapter()
        changeMumentSort()
        updateEveryMuments()
        musicDetailViewModel.changeSelectedSort(binding.tvSortLikeCount.text.toString())
    }

    private fun changeMumentSort() {
        binding.run {
            tvSortLikeCount.setOnClickListener { musicDetailViewModel?.changeSelectedSort(tvSortLikeCount.text.toString()) }
            tvSortLatest.setOnClickListener { musicDetailViewModel?.changeSelectedSort(tvSortLatest.text.toString()) }
            musicDetailViewModel?.selectedSort?.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
                tvSortLatest.changeSelectedSortTheme(it)
                tvSortLikeCount.changeSelectedSortTheme(it)
            }
        }
    }

    private fun setEveryMumentListAdapter() {
        binding.rvEveryMuments.run {
            adapter = MusicDetailMumentListAdapter()
            addItemDecoration(RecyclerviewItemDivider(0, 15.dpToPx(requireContext()), IS_VERTICAL))
        }
    }

    private fun updateEveryMuments() {
        musicDetailViewModel.mumentList.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
            Timber.e("${it.map { it.user.name }}")
            (binding.rvEveryMuments.adapter as MusicDetailMumentListAdapter).submitList(it)
        }
    }

    private fun AppCompatTextView.changeSelectedSortTheme(selectedSort: String) {
        isSelected = selectedSort == text.toString()
    }

}