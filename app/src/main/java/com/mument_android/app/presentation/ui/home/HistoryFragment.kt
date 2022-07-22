package com.mument_android.app.presentation.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mument_android.R
import com.mument_android.app.data.network.home.adapter.HistoryListAdapter
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.presentation.ui.detail.mument.MumentTagListAdapter
import com.mument_android.app.presentation.ui.home.viewmodel.HistoryViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHistoryBinding>()
    private lateinit var adapter: HistoryListAdapter
    private val historyViewModel: HistoryViewModel by viewModels()
    private val args: HistoryFragmentArgs by navArgs()

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

        historyViewModel.changeMusicId(args.musicId)

        binding.tvAsc.setOnClickListener {
            historyViewModel.changeSortType(true)
        }
        binding.tvDesc.setOnClickListener {
            historyViewModel.changeSortType(false)
        }
        adapter = HistoryListAdapter(requireContext())
        binding.rcHistory.adapter = adapter
        historyViewModel.getHistory()
        collectType()
    }


    private fun collectType() {
        lifecycleScope.launch {
            historyViewModel.musicDetailData.observe(viewLifecycleOwner){
                adapter.submitList(it.mumentHistory)
            }
            historyViewModel.selectSortType.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it) {
                        binding.tvAsc.setTextColor(requireContext().getColor(R.color.mument_color_purple1))
                        binding.tvDesc.setTextColor(requireContext().getColor(R.color.mument_color_gray1))
                        (binding.rcHistory.layoutManager as LinearLayoutManager).apply {
                            this.reverseLayout = false
                            this.stackFromEnd = false
                        }
                    } else {
                        binding.tvAsc.setTextColor(requireContext().getColor(R.color.mument_color_gray1))
                        binding.tvDesc.setTextColor(requireContext().getColor(R.color.mument_color_purple1))
                        (binding.rcHistory.layoutManager as LinearLayoutManager).apply {
                            this.reverseLayout = true
                            this.stackFromEnd = true
                        }
                    }
                }
        }
    }
}
