package com.mument_android.app.presentation.ui.home.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mument_android.app.presentation.ui.detail.music.MusicDetailFragment.Companion.MUSIC_ID
import com.mument_android.app.presentation.ui.home.viewmodel.HistoryViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.click
import com.mument_android.app.util.collectFlowWhenStarted
import com.mument_android.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHistoryBinding>()
    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHistoryBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.historyviewmodel = historyViewModel

        arguments?.getString(MUSIC_ID)?.let {
            historyViewModel.changeMusicId(it)
        }

        binding.llTouch.click { findNavController().popBackStack() }
        binding.ivBtnBack.click { findNavController().popBackStack() }
        binding.tvLatestOrder.click { historyViewModel.changeSortType(true) }
        binding.tvOldestOrder.click { historyViewModel.changeSortType(false) }

        binding.rcHistory.adapter = HistoryListAdapter(requireContext())
        historyViewModel.musicDetailData.observe(viewLifecycleOwner){
            (binding.rcHistory.adapter as HistoryListAdapter).submitList(it.mumentHistory)
        }

        historyViewModel.getHistory()
        collectType()


    }


    private fun collectType() {
        collectFlowWhenStarted(historyViewModel.selectSortType) { sort ->
            binding.tvLatestOrder.isSelected = sort
            binding.tvOldestOrder.isSelected = !sort
            (binding.rcHistory.layoutManager as LinearLayoutManager).apply {
                reverseLayout = !sort
                stackFromEnd = !sort
            }
        }
    }
}
