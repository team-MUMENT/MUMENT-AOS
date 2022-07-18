package com.mument_android.app.presentation.ui.detail.song

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.R
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_VERTICAL
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentSongDetailBinding

class SongDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentSongDetailBinding>()
    private val songDetailViewModel: SongDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
<<<<<<< HEAD
    ): View? = FragmentSongDetailBinding.inflate(inflater, container, false)?.let {
=======
    ): View = FragmentSongDetailBinding.inflate(inflater, container, false).let {
>>>>>>> ee996df ([FEAT] #54 - 곡 상세보기 UI 작업)
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.songDetailViewModel = songDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        songDetailViewModel.testImage.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {

        }
        setEveryMumentListAdapter()
        changeMumentSort()
        updateEveryMuments()
        songDetailViewModel.changeSelectedSort(binding.tvSortLikeCount.text.toString())
<<<<<<< HEAD
        songDetailViewModel.fetchDummyEveryMuments()
=======
>>>>>>> ee996df ([FEAT] #54 - 곡 상세보기 UI 작업)
    }

    private fun changeMumentSort() {
        binding.run {
            tvSortLikeCount.setOnClickListener { songDetailViewModel?.changeSelectedSort(tvSortLikeCount.text.toString()) }
            tvSortLatest.setOnClickListener { songDetailViewModel?.changeSelectedSort(tvSortLatest.text.toString()) }
            songDetailViewModel?.selectedSort?.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
                tvSortLatest.changeSelectedSortTheme(it)
                tvSortLikeCount.changeSelectedSortTheme(it)
            }
        }
    }

    private fun setEveryMumentListAdapter() {
        binding.rvEveryMuments.run {
            adapter = SongDetailMumentListAdapter()
            addItemDecoration(RecyclerviewItemDivider(0, 15.dpToPx(requireContext()), IS_VERTICAL))
        }
    }

    private fun updateEveryMuments() {
        songDetailViewModel.everyMuments.launchWhenCreated(viewLifecycleOwner.lifecycleScope) {
            (binding.rvEveryMuments.adapter as SongDetailMumentListAdapter).submitList(it)
        }
    }

    private fun AppCompatTextView.changeSelectedSortTheme(selectedSort: String) {
        isSelected = selectedSort == text.toString()
    }

}