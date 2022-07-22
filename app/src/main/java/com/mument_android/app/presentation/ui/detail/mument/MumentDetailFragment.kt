package com.mument_android.app.presentation.ui.detail.mument

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mument_android.R
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.customview.MumentDialogBuilder
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_GRIDLAYOUT
import com.mument_android.app.util.ViewUtils.applyVisibilityAnimation
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMumentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MumentDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMumentDetailBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()
    private val args: MumentDetailFragmentArgs by navArgs()
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
            tvGoToHistory.applyVisibilityAnimation(isUpward = true, reveal = true, durationTime = 700, delay = 150)
            ivBackButton.setOnClickListener { findNavController().popBackStack() }
        }

        if (!args.mumentId.isNullOrEmpty()) {
            viewModel.changeMumentId(args.mumentId)
            viewModel.fetchMumentDetailContent(args.mumentId)
        }

        binding.ivBackButton.setOnClickListener { findNavController().popBackStack() }
        setMumentTags()
        updateMumentTagList()
        changeLikeStatus()
        goToMumentHistory()
        showEditBottomSheet()
        successDeleteMument()
        goToMusicDetail()
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
        viewModel.mumentDetailContent.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            when(result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    (binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(result.data?.combineTags())
                    Timber.e(Thread.currentThread().name)
                }
                else -> {}
            }
        }
    }

    private fun changeLikeStatus() {
        binding.cbHeart.setOnClickListener {
            if(binding.cbHeart.isChecked) viewModel.likeMument() else viewModel.cancelLikeMument()
        }
    }

    private fun goToMumentHistory() {
        binding.tvGoToHistory.setOnClickListener {
            // Todo: 뮤멘트 히스토리로 이동
        }
    }

    private fun showEditBottomSheet() {
        binding.ivKebab.setOnClickListener {
            EditMumentDialogFragment(object : EditMumentDialogFragment.EditListener {
                override fun edit() {
                    viewModel.mumentDetailContent.value?.data?.let {
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
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            launch {
                viewModel.successDelete.collect {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun goToMusicDetail()  {
        binding.viewAlbumClickArea.setOnClickListener {
            val action = MumentDetailFragmentDirections.actionMumentDetailFragmentToLockerMusicDetailFragment(
                viewModel.mumentDetailContent.value?.data?.musicInfo?.id ?: ""
            )
            Timber.e(viewModel.mumentDetailContent.value?.data?.musicInfo?.id)
            findNavController().navigate(action)
        }
    }
}