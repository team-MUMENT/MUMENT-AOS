package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mument_android.R
import com.mument_android.app.data.network.home.adapter.HeardMumentListAdapter
import com.mument_android.app.data.network.home.adapter.ImpressiveEmotionListAdapter
import com.mument_android.app.presentation.ui.home.viewmodel.HomeViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class HomeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var heardAdapter: HeardMumentListAdapter
    private lateinit var impressiveAdapter: ImpressiveEmotionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = viewModel
        heardAdapter = HeardMumentListAdapter(requireContext()) { {} }
        impressiveAdapter = ImpressiveEmotionListAdapter(requireContext()) { {} }
        binding.rcHeard.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcImpressive.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcHeard.adapter = heardAdapter
        binding.rcImpressive.adapter = impressiveAdapter
        heardAdapter.submitList(viewModel.mument)
        impressiveAdapter.submitList(viewModel.mument)
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mumentDetailFragment)
        }
    }
}