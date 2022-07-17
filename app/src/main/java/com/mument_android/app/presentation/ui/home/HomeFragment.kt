package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mument_android.R
import com.mument_android.app.data.network.home.adapter.BannerListAdapter
import com.mument_android.app.data.network.home.adapter.HeardMumentListAdapter
import com.mument_android.app.data.network.home.adapter.ImpressiveEmotionListAdapter
import com.mument_android.app.presentation.ui.home.viewmodel.HomeViewModel
import com.mument_android.app.util.AutoClearedValue
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
            findNavController().navigate(R.id.action_homeFragment_to_mumentHistoryFragment)
            /*BottomSheetSearchFragment.newInstance().show(childFragmentManager, "Search")*/
        }
    }

    private fun setAdapter() {
        heardAdapter = HeardMumentListAdapter(requireContext()) { {} }
        impressiveAdapter = ImpressiveEmotionListAdapter(requireContext()) { {} }
        bannerAdapter = BannerListAdapter(viewModel.bannerData)
    }

    private fun setRecyclerView() {
        binding.rcHeard.adapter = heardAdapter
        binding.rcImpressive.adapter = impressiveAdapter
        binding.vpBanner.adapter = bannerAdapter
        binding.vpBanner.offscreenPageLimit = 1
        binding.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.bannerIndexChange(position)
            }
        })
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.bannerNumIncrease.collect { index ->
                binding.vpBanner.setCurrentItem(
                    index, true
                )
            }
        }
    }

    private fun setListData() {
        //bannerAdapter.()
        heardAdapter.submitList(viewModel.mument)
        impressiveAdapter.submitList(viewModel.mument)
    }
}