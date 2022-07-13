package com.mument_android.app.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentMumentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

// Todo: 좋아요 버튼 클릭시, 뮤멘트 히스토리 보러가기 눌렀을 때, Kebab Button 클릭 액션 처리

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mumentDetailViewModel= viewModel
        binding.rvEmotionalTags.adapter = EmotionalTagListAdapter()
        setMumentTagList()
    }

    private fun setMumentTagList() {
        viewModel.mumentDetailContent.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            when(result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    // Fixme: orientation을 Horizontal로만 바뀌면 RecyclerView가 보이지 않는 현상
                    (binding.rvEmotionalTags.adapter as EmotionalTagListAdapter)
                        .submitList( result.data?.emotionalTags?.map { requireContext().getString(it.tag) })
                }
            }
        }
    }
}