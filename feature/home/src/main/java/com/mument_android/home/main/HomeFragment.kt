package com.mument_android.home.main

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.mument_android.core.util.Constants.FROM_NOTIFICATION_TO_MUMENT_DETAIL
import com.mument_android.core.util.Constants.FROM_SEARCH
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.START_NAV_KEY
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ext.setOnSingleClickListener
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
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
    private val notifyBroadcastReceiver = NotifyBroadCastReceiver()

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

    override fun onStart() {
        super.onStart()
        requireContext().registerReceiver(notifyBroadcastReceiver, IntentFilter("NEW_INTENT"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkCurrentBackStack()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = viewModel
        bindData()
    }

    private fun checkCurrentBackStack() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle?.let { savedStateHandle ->
                savedStateHandle.getLiveData<String>(START_NAV_KEY, "")
                    .observe(viewLifecycleOwner) { startNav ->
                        when (startNav) {
                            FROM_NOTIFICATION_TO_MUMENT_DETAIL -> viewModel.emitEvent(HomeEvent.ReEntryToNotificationView)
                            FROM_SEARCH -> viewModel.emitEvent(HomeEvent.OnClickSearch)
                        }
                    }
            }
    }

    private val searchMusicLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let { music ->
                    result.data?.getStringExtra(START_NAV_KEY).takeIf { it == FROM_SEARCH }?.let {
                        viewModel.emitEvent(HomeEvent.ReEntryToSearchView(music, it))
                    }
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
                viewModel.emitEvent(
                    HomeEvent.OnClickTodayMument(
                        it.mumentId,
                        it.extractMusicInfo()
                    )
                )
            }
            FirebaseAnalyticsUtil.firebaseLog(
                "home_activity_type",
                "type",
                " home_todaymu"
            )
        }
        binding.ivLogo.setOnSingleClickListener {
            viewModel.emitEvent(HomeEvent.OnClickLogo)
        }
    }

    private fun setAdapter() {
        heardAdapter = HeardMumentListAdapter(requireContext()) { mument ->
            mument.music.toMusicInfoEntity()?.let { musicInfoEntity ->
                HomeEvent.OnClickHeardMument(
                    mument.mumentId,
                    musicInfoEntity
                )
            }?.let { event ->
                viewModel.emitEvent(
                    event
                )
            }
        }
        impressiveAdapter = ImpressiveEmotionListAdapter(requireContext()) { mument ->
            mument.music.toMusicInfoEntity()?.let { musicInfoEntity ->
                HomeEvent.OnClickRandomMument(
                    mument._id,
                    musicInfoEntity
                )
            }?.let { event ->
                viewModel.emitEvent(
                    event
                )
            }
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
                    startActivity(
                        Intent(
                            requireActivity(),
                            NotifyActivity::class.java
                        )
                    )
                }
                HomeSideEffect.GoToSearchActivity -> {
                    searchMusicLauncher.launch(
                        Intent(
                            requireActivity(),
                            SearchActivity::class.java
                        )
                    )
                    FirebaseAnalyticsUtil.firebaseLog(
                        "home_activity_type",
                        "type",
                        " home_search"
                    )
                }
                is HomeSideEffect.NavToMusicDetail -> {
                    musicDetailNavigatorProvider.fromHomeToMusicDetail(
                        effect.musicInfo,
                        effect.startNav
                    )
                    FirebaseAnalyticsUtil.firebaseLog(
                        "home_activity_type",
                        "type",
                        " home_rec_song"
                    )
                }
                is HomeSideEffect.NavToMumentDetail -> {
                    mumentDetailNavigatorProvider.moveHomeToMumentDetail(
                        effect.mumentId,
                        effect.musicInfo,
                        effect.startNav
                    )
                }
                is HomeSideEffect.Toast -> requireContext().showToast(effect.message)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(notifyBroadcastReceiver)
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkNotifyExist()
        viewModel.bannerIndexChange(0)
        binding.vpBanner.setCurrentItem(0, false)
    }

    inner class NotifyBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p1?.action == "NEW_INTENT") {
                viewModel.checkNotifyExist()
            }
        }
    }
}