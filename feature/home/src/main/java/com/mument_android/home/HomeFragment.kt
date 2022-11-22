package com.mument_android.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.home.adapters.BannerListAdapter
import com.mument_android.home.adapters.HeardMumentListAdapter
import com.mument_android.home.adapters.ImpressiveEmotionListAdapter
import com.mument_android.home.databinding.FragmentHomeBinding
import com.mument_android.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        binding.clCard.root.setOnClickListener {
            viewModel.todayMument.value?.mumentId?.let { showMumentDetail(it) }
        }
    }

    private fun bindData() {
        setAdapter()
        setRecyclerView()
        setListData()

        binding.tvSearch.setOnClickListener {
            Intent(requireActivity(), SearchActivity::class.java).apply {
                startActivity(this)
            }
            //findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        val homeFrame = requireParentFragment().requireParentFragment()
        //TODO Navi
        /*(homeFrame as HomeFrameFragment).arguments?.getString("musicId")?.let { musicId ->
            if (musicId.isNotEmpty()) {
                val bundle = Bundle().also { it.putString(MUSIC_ID, musicId) }
                findNavController().navigate(
                    R.id.action_homeFragment_to_musicDetailFragment,
                    bundle
                )
            }
        }*/
    }

    // TODO NAVI
    private fun setAdapter() {
        heardAdapter = HeardMumentListAdapter(requireContext()) { mument ->
            /*val bundle = Bundle().also { it.putString(MUMENT_ID, mument.mumentId) }
            findNavController().navigate(R.id.action_homeFragment_to_mumentDetailFragment, bundle)*/
        }
        impressiveAdapter = ImpressiveEmotionListAdapter(requireContext()) { mument ->
            /*val bundle = Bundle().also { it.putString(MUMENT_ID, mument._id) }
            findNavController().navigate(R.id.action_homeFragment_to_mumentDetailFragment, bundle)*/
        }
        bannerAdapter =
            BannerListAdapter(viewModel.bannerData.value?.toMutableList() ?: listOf()) { musicId ->
                /*val bundle = Bundle().also { it.putString(MUSIC_ID, musicId) }
                findNavController().navigate(R.id.action_homeFragment_to_musicDetailFragment, bundle)*/
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
    }

    private fun setListData() {
        collectFlowWhenStarted(viewModel.bannerNumIncrease) { index ->
            binding.vpBanner.setCurrentItem(index, true)
        }
        collectFlowWhenStarted(viewModel.randomMument) {
            if (it != null) {
                impressiveAdapter.submitList(it.mumentList)
                binding.rcImpressive.adapter = impressiveAdapter
                binding.tvImpressive.text = it.title
            }
        }
        collectFlowWhenStarted(viewModel.knownMument) {
            heardAdapter.submitList(it)
            binding.rcHeard.adapter = heardAdapter
        }
        collectFlowWhenStarted(viewModel.bannerData) { banner ->
            bannerAdapter.data = banner.map {
                BannerEntity(
                    it._id,
                    it.displayDate,
                    Music(it.music._id, it.music.name, it.music.artist, it.music.image),
                    it.tagTitle.replace("\\n", "\n")
                )
            }
            bannerAdapter.notifyDataSetChanged()
        }
        collectFlowWhenStarted(viewModel.todayMument) { today ->
            if (today != null) {
                val data = today.cardTag.map { tag ->
                    if (tag < 200) TagEntity(
                        TagEntity.TAG_IMPRESSIVE,
                        ImpressiveTag.findImpressiveStringTag(tag),
                        tag
                    )
                    else TagEntity(
                        TagEntity.TAG_EMOTIONAL,
                        EmotionalTag.findEmotionalStringTag(tag),
                        tag
                    )
                }
                binding.clCard.rvTags.adapter = MumentTagListAdapter()
                (binding.clCard.rvTags.adapter as MumentTagListAdapter).submitList(data)
            }
        }
    }

    // TODO NAVI
    private fun showMumentDetail(mumentId: String) {
        /*val bundle = Bundle().also { it.putString(MUMENT_ID, mumentId) }
        findNavController().navigate(R.id.action_homeFragment_to_mumentDetailFragment, bundle)*/
    }

}