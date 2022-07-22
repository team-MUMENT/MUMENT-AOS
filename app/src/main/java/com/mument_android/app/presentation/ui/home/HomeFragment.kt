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
import com.mument_android.app.data.dto.home.Banner
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.data.network.home.adapter.BannerListAdapter
import com.mument_android.app.data.network.home.adapter.HeardMumentListAdapter
import com.mument_android.app.data.network.home.adapter.ImpressiveEmotionListAdapter
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.presentation.ui.detail.mument.MumentDetailFragment.Companion.FROM_HOME
import com.mument_android.app.presentation.ui.detail.mument.MumentTagListAdapter
import com.mument_android.app.presentation.ui.detail.music.HomeMusicDetailFragmentDirections
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
        val homeFrame = requireParentFragment().requireParentFragment()

        (homeFrame as HomeFrameFragment).arguments?.getString("musicId")?.let {
            val action = HomeFragmentDirections.actionHomeFragmentToHomeMusicDetailFragment(it)
            findNavController().navigate(action)
        }
        bindData()
        binding.clCard.root.setOnClickListener {
            viewModel.todayMument.value?.mumentId?.let { showMumentDetail(it) }
        }
    }

    private fun bindData() {
        setAdapter()
        setRecyclerView()
        setListData()

        binding.tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            /*BottomSheetSearchFragment.newInstance().show(childFragmentManager, "Search")*/
        }
    }

    private fun setAdapter() {
        heardAdapter = HeardMumentListAdapter(requireContext()) {
            HomeFragmentDirections.actionHomeFragmentToHomeMumentDetailFragment(it.mumentId).apply {
                findNavController().navigate(this)
            }
        }
        impressiveAdapter = ImpressiveEmotionListAdapter(requireContext()) {
            HomeFragmentDirections.actionHomeFragmentToHomeMumentDetailFragment(it._id).apply {
                findNavController().navigate(this)
            }
        }
        bannerAdapter = BannerListAdapter(viewModel.bannerData.value!!.toMutableList()) {
            val action = HomeFragmentDirections.actionHomeFragmentToHomeMusicDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.vpBanner.adapter = bannerAdapter
    }

    private fun setRecyclerView() {
        binding.vpBanner.offscreenPageLimit = 2
        binding.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.bannerIndexChange(position)
            }
        })
        lifecycleScope.launchWhenCreated {
            viewModel.bannerNumIncrease.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { index ->
                    binding.vpBanner.setCurrentItem(index, true)
                }
        }
    }

    private fun setListData() {
        viewModel.randomMument.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                impressiveAdapter.submitList(it)
                binding.rcImpressive.adapter = impressiveAdapter
            }
        }
        viewModel.knownMument.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                heardAdapter.submitList(it)
                binding.rcHeard.adapter = heardAdapter
            }
        }
        viewModel.bannerData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                bannerAdapter.data = it.toMutableList().map {
                    Banner(
                        it._id,
                        it.displayDate,
                        Music(it.music._id, it.music.name, it.music.artist, it.music.image),
                        it.tagTitle.replace("\\n", "\n")
                    )

                }
                bannerAdapter.notifyDataSetChanged()
            }
        }
        viewModel.todayMument.observe(viewLifecycleOwner){
            if(it != null){
                val data = it.cardTag.map {
                    if(it < 200) TagEntity(TagEntity.TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(it), it)
                    else TagEntity(TagEntity.TAG_EMOTIONAL, EmotionalTag.findEmotionalStringTag(it), it)
                }
                binding.clCard.rvTags.adapter = MumentTagListAdapter()
                (binding.clCard.rvTags.adapter as MumentTagListAdapter).submitList(data)
            }
        }
    }

    private fun showMumentDetail(mumentId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToHomeMumentDetailFragment(mumentId)
        findNavController().navigate(action)
    }

}