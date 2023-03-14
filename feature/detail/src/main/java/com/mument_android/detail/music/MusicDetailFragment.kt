package com.mument_android.detail.music

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.angdroid.navigation.HistoryNavigatorProvider
import com.angdroid.navigation.MoveRecordProvider
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.mument_android.core.util.Constants
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.START_NAV_KEY
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.detail.databinding.FragmentMusicDetailBinding
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.detail.mument.contract.MumentDetailContract
import com.mument_android.detail.mument.listener.MumentClickListener
import com.mument_android.detail.mument.listener.StackProvider
import com.mument_android.detail.music.MusicDetailContract.MusicDetailEffect
import com.mument_android.detail.music.MusicDetailContract.MusicDetailEvent
import com.mument_android.domain.entity.music.MusicInfoEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MusicDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMusicDetailBinding>()
    private val musicDetailViewModel: MusicDetailViewModel by viewModels()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    @Inject
    lateinit var musicDetailNavigatorProvider: MusicDetailNavigatorProvider

    @Inject
    lateinit var recordProvider: MoveRecordProvider

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

    @Inject
    lateinit var historyNavigatorProvider: HistoryNavigatorProvider

    lateinit var musicDetailMumentHeaderAdapter: MusicDetailMumentHeaderAdapter

    lateinit var musicDetailListHeaderAdapter: MusicDetailListHeaderAdapter

    lateinit var musicDetailMumentListAdapter: MusicDetailMumentListAdapter

    private lateinit var musicDetailConcatAdapter: ConcatAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                musicDetailViewModel.emitEvent(MusicDetailEvent.OnClickBackButton)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMusicDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicDetailViewModel = musicDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        clickBackButton()
        setEntireMumentListAdapter()
        updateView()
        collectEffect()
        receiveMusicId()

    }

    private fun clickBackButton() {
        binding.ivBack.setOnClickListener {
            musicDetailViewModel.emitEvent(MusicDetailEvent.OnClickBackButton)
        }
    }

    private fun receiveMusicId() {
        arguments?.getParcelable<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let {
            musicDetailViewModel.emitEvent(MusicDetailEvent.ReceiveRequestMusicInfo(it))
        }
        arguments?.getString(START_NAV_KEY)?.let {
            musicDetailViewModel.emitEvent(MusicDetailEvent.ReceiveStartNav(it))
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(START_NAV_KEY)?.observe(viewLifecycleOwner) {
            musicDetailViewModel.emitEvent(MusicDetailEvent.ReceiveStartNav(it))
        }
    }

    private fun updateView() {
        collectFlowWhenStarted(musicDetailViewModel.viewState) { state ->
            musicDetailMumentHeaderAdapter.myMumentInfo = state.myMumentInfo
            musicDetailMumentListAdapter.submitList(state.mumentList)
        }
    }

    private fun collectEffect() {
        collectFlowWhenStarted(musicDetailViewModel.effect) { effect ->
            when (effect) {
                is MusicDetailEffect.ShowToast -> requireContext().showToast(effect.msg)
                is MusicDetailEffect.PopBackStack -> {
                    musicDetailNavigatorProvider.musicDetailPopBackStack(effect.startNav)
                }
                MusicDetailEffect.CompleteLikeMument -> {}
            }
        }
    }

    private fun setEntireMumentListAdapter() {
        musicDetailMumentHeaderAdapter = MusicDetailMumentHeaderAdapter(
            { musicDetailViewModel.viewState.value.musicInfo?.let {
                        musicDetailViewModel.viewState.value.myMumentInfo?.user?.userId?.let { userId ->
                            getResultText.launch(
                                Intent(
                                    requireActivity(),
                                    HistoryActivity::class.java
                                ).apply {
                                    putExtra("music", it.toMusic())
                                    putExtra("userId", userId.toInt())
                                })
                        }
                    }
            }, object : MumentClickListener {
                override fun showMumentDetail(mumentId: String) {
                    musicDetailViewModel.viewState.value.musicInfo?.let { musicInfoEntity ->
                        mumentDetailNavigatorProvider.musicDetailToMumentDetail(
                            mumentId,
                            musicInfo = musicInfoEntity,
                            arguments?.getString(START_NAV_KEY)
                        )
                    }
                }

                override fun likeMument(mumentId: String) {
                    musicDetailViewModel.emitEvent(
                        MusicDetailEvent.CheckLikeMument(mumentId)
                    )
                }

                override fun cancelLikeMument(mumentId: String) {
                    musicDetailViewModel.emitEvent(
                        MusicDetailEvent.UnCheckLikeMument(
                            musicDetailViewModel.viewState.value.myMumentInfo!!.mumentId
                        )
                    )
                }
            })
        musicDetailMumentListAdapter = MusicDetailMumentListAdapter(object : MumentClickListener {
            override fun showMumentDetail(mumentId: String) {
                musicDetailViewModel.viewState.value.musicInfo?.let { musicInfo ->
                    mumentDetailNavigatorProvider.musicDetailToMumentDetail(mumentId, musicInfo, musicDetailViewModel.viewState.value.startNav)
                }
            }

            override fun likeMument(mumentId: String) {
                musicDetailViewModel.emitEvent(MusicDetailEvent.CheckLikeItemMument(mumentId))
            }

            override fun cancelLikeMument(mumentId: String) {
                musicDetailViewModel.emitEvent(MusicDetailEvent.UnCheckLikeItemMument(mumentId))
            }
        })
        musicDetailListHeaderAdapter = MusicDetailListHeaderAdapter { event ->
            musicDetailViewModel.emitEvent(event)
        }
        musicDetailConcatAdapter = ConcatAdapter(
            musicDetailMumentHeaderAdapter,
            musicDetailListHeaderAdapter,
            musicDetailMumentListAdapter
        )
        binding.rcConcat.adapter = musicDetailConcatAdapter
    }

    private val getResultText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                it.data?.getParcelableExtra<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let { music ->
                    it.data?.getStringExtra(MUMENT_ID)?.let { mumentId ->
                        mumentDetailNavigatorProvider.musicDetailToMumentDetail(mumentId, music, arguments?.getString(START_NAV_KEY))
                    }
                }
            }
        }


    override fun onDestroyView() {
        onBackPressedCallback.remove()
        super.onDestroyView()
    }

    companion object {
        const val MUSIC_ID = "MUSIC_ID"
    }
}