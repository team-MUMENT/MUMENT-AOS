package com.mument_android.app.presentation.ui.detail.mument

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mument_android.BuildConfig
import com.mument_android.R
import com.startup.core_dependent.ui.MumentDialogBuilder
import com.mument_android.app.presentation.ui.detail.mument.navigator.EditMumentNavigatorProvider
import com.mument_android.app.presentation.ui.detail.music.MusicDetailFragment.Companion.MUSIC_ID
import com.mument_android.app.util.StartDestinationChecker.isFromHome
import com.startup.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.databinding.FragmentMumentDetailBinding
import com.startup.core.network.ApiResult
import com.startup.core_dependent.ext.click
import com.startup.core_dependent.util.AutoClearedValue
import com.startup.core_dependent.util.RecyclerviewItemDivider
import com.startup.core_dependent.util.RecyclerviewItemDivider.Companion.IS_GRIDLAYOUT
import com.startup.core_dependent.util.ViewUtils.applyVisibilityAnimation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MumentDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMumentDetailBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()
    @Inject lateinit var editMumentNavigatorProvider: EditMumentNavigatorProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMumentDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            mumentDetailViewModel= viewModel
            ivBackButton.setOnClickListener { findNavController().popBackStack() }
        }

        arguments?.getString(MUMENT_ID)?.let {
            viewModel.changeMumentId(it)
            viewModel.fetchMumentDetailContent(it)
        }

        showMumentHistory()
        setMumentTags()
        updateMumentTagList()
        changeLikeStatus()
        showEditBottomSheet()
        successDeleteMument()
        goToMusicDetail()
        goToHistory()
    }

    private fun setMumentTags() {
        with(binding) {
            rvMumentTags.adapter = MumentTagListAdapter()
            rvMumentTags.layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
            rvMumentTags.addItemDecoration(RecyclerviewItemDivider(7, 12, IS_GRIDLAYOUT))
        }
    }

    private fun updateMumentTagList() {
        collectFlowWhenStarted(viewModel.mumentDetailContent) { result ->
            when(result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    binding.ivKebab.visibility = if (result.data?.writerInfo?.userId == BuildConfig.USER_ID) View.VISIBLE else View.GONE
                    (binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(result.data?.combineTags())
                }
                else -> {}
            }
        }
    }

    private fun changeLikeStatus() {
        binding.cbHeart.click {
            if(binding.cbHeart.isChecked) viewModel.likeMument() else viewModel.cancelLikeMument()
        }
    }

    private fun showEditBottomSheet() {
        binding.ivKebab.click {
            EditMumentDialogFragment(object : EditMumentDialogFragment.EditListener {
                override fun edit() {
                    viewModel.mumentDetailContent.value.data?.let {
                        editMumentNavigatorProvider.editMument(viewModel.mumentId.value, it)
                    }
                }

                override fun delete() {
                    MumentDialogBuilder()
                        .setHeader("삭제하시겠어요?")
                        .setAllowListener { viewModel.deleteMument() }
                        .build()
                        .show(childFragmentManager, this@MumentDetailFragment.tag)
                }
            }).show(childFragmentManager, this.tag)
        }
    }

    private fun successDeleteMument() {
        collectFlowWhenStarted(viewModel.successDelete) {
            findNavController().popBackStack()
        }
    }

    private fun showMumentHistory() {
        viewModel.hasWritten.observe(viewLifecycleOwner) { hasWritten ->
            if (hasWritten == true) {
                binding.tvGoToHistory.applyVisibilityAnimation(isUpward = true, reveal = true, durationTime = 700, delay = 150)
            }
        }
    }

    private fun goToMusicDetail()  {
        binding.viewAlbumClickArea.setOnClickListener {
            viewModel.mumentDetailContent.value.data?.musicInfo?.id?.let { musicId ->
                Bundle().also {
                    it.putString(MUSIC_ID, musicId)
                    val actionId = if (isFromHome()) R.id.action_mumentDetailFragment_to_musicDetailFragment_home else R.id.action_mumentDetailFragment_to_musicDetailFragment_locker
                    findNavController().navigate(actionId, it)
                }
            }
        }
    }

    private fun goToHistory() {
        binding.tvGoToHistory.setOnClickListener {
            viewModel.mumentDetailContent.value.data?.musicInfo?.id?.let { musicId ->
                Bundle().also {
                    it.putString(MUSIC_ID, musicId)
                    val actionId = if (isFromHome()) R.id.action_mumentDetailFragment_to_historyFragment_home else R.id.action_mumentDetailFragment_to_historyFragment_locker
                    findNavController().navigate(actionId, it)
                }
            }
        }
    }

    companion object {
        const val MUMENT_ID = "MUMENT_ID"
    }
}