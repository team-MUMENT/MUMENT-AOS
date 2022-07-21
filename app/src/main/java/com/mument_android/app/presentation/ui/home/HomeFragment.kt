package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mument_android.R
import com.mument_android.app.data.network.home.adapter.BannerListAdapter
import com.mument_android.app.data.network.home.adapter.HeardMumentListAdapter
import com.mument_android.app.data.network.home.adapter.ImpressiveEmotionListAdapter
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.home.viewmodel.HomeViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenStarted
import com.mument_android.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var heardAdapter: HeardMumentListAdapter
    private lateinit var impressiveAdapter: ImpressiveEmotionListAdapter
    private lateinit var bannerAdapter: BannerListAdapter

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
        bindData()
    }

    private fun bindData() {
        setAdapter()
        setRecyclerView()
        setListData()
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mumentDetailFragment)
        }
        binding.tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            /*BottomSheetSearchFragment.newInstance().show(childFragmentManager, "Search")*/
        }
    }

    private fun setAdapter() {
        heardAdapter = HeardMumentListAdapter(requireContext()) { {} }
        impressiveAdapter = ImpressiveEmotionListAdapter(requireContext()) { {} }
        bannerAdapter = BannerListAdapter(viewModel.bannerData.value!!.toMutableList())
        binding.vpBanner.adapter = bannerAdapter
    }

    private fun setRecyclerView() {
        binding.vpBanner.offscreenPageLimit = 1
        binding.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.bannerIndexChange(position)
            }
        })
        lifecycleScope.launchWhenCreated {
            viewModel.bannerNumIncrease.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { index ->
                    binding.vpBanner.setCurrentItem(
                        index, true
                    )
                }
        }
    }

    private fun setListData() {
        viewModel.randomMument.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                impressiveAdapter.submitList(it)
                binding.rcImpressive.adapter = impressiveAdapter
                Timber.d("get Random ${impressiveAdapter.currentList}")
            }
        }
        viewModel.knownMument.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                heardAdapter.submitList(it)
                binding.rcHeard.adapter = heardAdapter
                Timber.d("get Known ${heardAdapter.currentList}")
            }
        }
        viewModel.bannerData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                bannerAdapter.data = it.toMutableList()
                bannerAdapter.notifyDataSetChanged()
            }
        }
        viewModel.todayMument.observe(viewLifecycleOwner) {
        }
    }
}