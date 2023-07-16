package com.mument_android.home.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.mument_android.core.util.Constants.FROM_NOTIFICATION_TO_MUMENT_DETAIL
import com.mument_android.core.util.Constants.FROM_SEARCH
import com.mument_android.core.util.Constants.START_NAV_KEY
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ext.setOnSingleClickListener
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.FirebaseAnalyticsUtil
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.domain.entity.home.BannerEntity
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var heardAdapter: HeardMumentListAdapter
    private lateinit var impressiveAdapter: ImpressiveEmotionListAdapter

    @Inject
    lateinit var dataStoreManager: DataStoreManager

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
        requireActivity().sendBroadcast(Intent("KILL_HISTORY").apply {
            putExtra("KILL_HISTORY", true)
        })
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
            //홈탭에서 -> 오늘의 뮤멘트 터치
            FirebaseAnalyticsUtil.firebaseLog(
                "home_activity_type",
                "type",
                "home_todaymu"
            )

            lifecycleScope.launch {
                //최초에 홈 큐레이션 클릭 GA
                if (dataStoreManager.isFirstFlow.firstOrNull() == true) {
                    FirebaseAnalyticsUtil.firebaseFirstVisitLog("direct_curation")
                    dataStoreManager.writeIsFirst(false)
                }
            }

            //뮤멘트 상세보기에 진입했을 때 GA
            FirebaseAnalyticsUtil.firebaseMumentDetailLog("from_home")
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
                lifecycleScope.launch {
                    //최초에 홈 큐레이션 클릭 GA
                    if (dataStoreManager.isFirstFlow.firstOrNull() == true) {
                        Log.e("최초에", "홈 큐레이션 클릭 : 다시 들은 곡")
                        FirebaseAnalyticsUtil.firebaseFirstVisitLog("direct_curation")
                        dataStoreManager.writeIsFirst(false)
                    }
                }
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
                lifecycleScope.launch {
                    //최초에 홈 큐레이션 클릭 GA
                    if (dataStoreManager.isFirstFlow.firstOrNull() == true) {
                        Log.e("최초에", "홈 큐레이션 클릭 : 인상적인 태그")
                        FirebaseAnalyticsUtil.firebaseFirstVisitLog("direct_curation")
                        dataStoreManager.writeIsFirst(false)
                    }
                }

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
                        //최초 접속 시 홈 큐레이션 클릭
                        lifecycleScope.launch {
                            if (dataStoreManager.isFirstFlow.firstOrNull() == true) {
                                FirebaseAnalyticsUtil.firebaseFirstVisitLog("direct_curation")
                                dataStoreManager.writeIsFirst(false)
                            }
                        }

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
                        Intent(requireActivity(), NotifyActivity::class.java)
                    )
                }
                HomeSideEffect.GoToSearchActivity -> {
                    requireContext().startActivity(
                        Intent(
                            requireActivity(),
                            SearchActivity::class.java
                        )
                    )

                    //홈탭에서 -> 검색 터치
                    FirebaseAnalyticsUtil.firebaseLog(
                        "home_activity_type",
                        "type",
                        "home_search"
                    )

                    lifecycleScope.launch {
                        //최초에 검색 클릭 GA
                        if (dataStoreManager.isFirstFlow.firstOrNull() == true) {
                            FirebaseAnalyticsUtil.firebaseFirstVisitLog("direct_search")
                            dataStoreManager.writeIsFirst(false)
                        }
                    }
                }
                is HomeSideEffect.NavToMusicDetail -> {
                    musicDetailNavigatorProvider.fromHomeToMusicDetail(
                        effect.musicInfo,
                        effect.startNav
                    )
                    //홈탭에서 -> 추천 곡 터치
                    FirebaseAnalyticsUtil.firebaseLog(
                        "home_activity_type",
                        "type",
                        "home_rec_song"
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
    override fun onResume() {
        super.onResume()
        viewModel.checkNotifyExist()
        viewModel.bannerIndexChange(0)
        binding.vpBanner.setCurrentItem(0, false)
    }

}