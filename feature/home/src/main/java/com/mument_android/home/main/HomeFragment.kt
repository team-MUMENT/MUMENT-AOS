package com.mument_android.home.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.home.adapters.BannerListAdapter
import com.mument_android.home.adapters.HeardMumentListAdapter
import com.mument_android.home.adapters.ImpressiveEmotionListAdapter
import com.mument_android.home.databinding.FragmentHomeBinding
import com.mument_android.home.main.HomeContract.HomeEvent
import com.mument_android.home.main.HomeContract.HomeSideEffect
import com.mument_android.home.notify.NotifyActivity
import com.mument_android.home.search.SearchActivity
import com.mument_android.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var heardAdapter: HeardMumentListAdapter
    private lateinit var impressiveAdapter: ImpressiveEmotionListAdapter

    @Inject
    lateinit var musicDetailNavigatorProvider: MusicDetailNavigatorProvider

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

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

    private val searchMusicLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            it.data?.getParcelableExtra<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.apply {
                musicDetailNavigatorProvider.fromHomeToMusicDetail(this)
            }
        }
    }

    private fun bindData() {
        setAdapter()
        setListData()
        receiveEffect()
        binding.tvSearch.setOnClickListener {
            viewModel.emitEvent(HomeEvent.OnClickSearch)
        }
        binding.ivNotify.setOnClickListener {
            viewModel.emitEvent(HomeEvent.OnClickNotification)
        }
        binding.clCard.root.setOnClickListener {
            viewModel.homeViewState.value.todayMumentEntity?.let {
                viewModel.emitEvent(HomeEvent.OnClickTodayMument(it.mumentId, it.extractMusicInfo()))
            }
        }
    }

    private fun setAdapter() {
        heardAdapter = HeardMumentListAdapter(requireContext()) { mument ->
            viewModel.emitEvent(HomeEvent.OnClickHeardMument(mument.mumentId, mument.music.toMusicInfoEntity()))
        }
        impressiveAdapter = ImpressiveEmotionListAdapter(requireContext()) { mument ->
            viewModel.emitEvent(HomeEvent.OnClickRandomMument(mument._id, mument.music.toMusicInfoEntity()))
        }
        binding.rcHeard.adapter = heardAdapter
        binding.rcImpressive.adapter = impressiveAdapter
    }

    private fun setBannerCallBack() {
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
        collectFlowWhenStarted(viewModel.homeViewState) { homeViewState ->
            with(homeViewState) {
                emotionMumentEntity?.let {
                    impressiveAdapter.submitList(it.mumentList)
                    binding.tvImpressive.text = it.title
                }
                todayMumentEntity?.let { today ->
                    binding.clCard.rvTags.adapter = MumentTagListAdapter()
                    (binding.clCard.rvTags.adapter as MumentTagListAdapter).submitList(today.cardTag)
                }
                heardMumentEntity?.let { heard ->
                    heardAdapter.submitList(heard)
                }
                bannerEntity?.let { banner ->
                    binding.vpBanner.adapter = BannerListAdapter(banner.map {
                        BannerEntity(
                            it.displayDate,
                            Music(it.music._id, it.music.name, it.music.artist, it.music.image),
                            it.tagTitle.replace("\\n", "\n")
                        )
                    }) { music ->
                        viewModel.emitEvent(HomeEvent.OnClickBanner(music.toMusicInfoEntity()))
                    }
                    setBannerCallBack()
                }
            }
        }
    }

    private fun receiveEffect() {
        collectFlowWhenStarted(viewModel.effect) { effect ->
            when (effect) {
                HomeSideEffect.GoToNotification -> {
                    startActivity(Intent(requireActivity(), NotifyActivity::class.java))
                }
                HomeSideEffect.GoToSearchActivity -> {
                    searchMusicLauncher.launch(Intent(requireActivity(), SearchActivity::class.java))
                }
                is HomeSideEffect.NavToMusicDetail -> {
                    musicDetailNavigatorProvider.fromHomeToMusicDetail(effect.music)
                }
                is HomeSideEffect.NavToMumentDetail -> {
                    mumentDetailNavigatorProvider.moveHomeToMumentDetail(effect.mumentId, effect.musicInfo)
                }
                is HomeSideEffect.Toast -> requireContext().showToast(effect.message)
            }
        }
    }

    override fun onResume() {
        super.onResume()
/*
        val homeFrame = requireParentFragment().requireParentFragment()
        //TODO Navi
        (homeFrame as HomeFragment).arguments?.getString("musicId")?.let { musicId ->
            if (musicId.isNotEmpty()) {
                val bundle = Bundle().also { it.putString(MUSIC_ID, musicId) }
                findNavController().navigate(
                    R.id.action_homeFragment_to_musicDetailFragment,
                    bundle
                )
            }
        }*/
    }

    companion object {
        const val MUMENT_ID = "MUMENT_ID"
        const val MUSIC_ID = "MUSIC_ID"
    }
}