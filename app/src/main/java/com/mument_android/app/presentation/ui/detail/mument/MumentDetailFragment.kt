package com.mument_android.app.presentation.ui.detail.mument

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.customview.MumentDialogBuilder
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.RecyclerviewItemDivider
import com.mument_android.app.util.RecyclerviewItemDivider.Companion.IS_GRIDLAYOUT
import com.mument_android.app.util.ViewUtils.applyVisibilityAnimation
import com.mument_android.app.util.ViewUtils.dpToPx
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMumentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MumentDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMumentDetailBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMumentDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvGoToHistory.applyVisibilityAnimation(isUpward = true, reveal = true, durationTime = 700, delay = 150)
        setMumentTags()
        updateMumentTagList()
        changeLikeStatus()
        goToMumentHistory()
        showEditBottomSheet()
    }

    private fun setMumentTags() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            mumentDetailViewModel= viewModel

            rvMumentTags.adapter = MumentTagListAdapter()
            rvMumentTags.layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
            rvMumentTags.addItemDecoration(RecyclerviewItemDivider(7.dpToPx(requireContext()), 5.dpToPx(requireContext()), IS_GRIDLAYOUT))
        }
    }

    private fun updateMumentTagList() {
        viewModel.mumentDetailContent.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            when(result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    (binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(result.data?.combineTags())
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

    // Todo: 수정하기, 삭제하기 BottomSheet 노출
    private fun showEditBottomSheet() {
        binding.ivKebab.setOnClickListener {
            MumentDialogBuilder()
                .setHeader("수정을 취소하시겠어요?")
                .setBody("확인 선택 시 변경사항이 저장되지 않습니다.")
                .setAllowListener { Timber.e("allow") }
                .setCancelListener { Timber.e("cancel") }
                .build()
                .show(childFragmentManager, this.tag)
        }
    }
}