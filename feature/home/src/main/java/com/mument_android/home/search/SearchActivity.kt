package com.mument_android.home.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.angdroid.navigation.MoveMusicDetailNavigatorProvider
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.core_dependent.util.TransitionMode
import com.mument_android.home.R
import com.mument_android.home.adapters.SearchListAdapter
import com.mument_android.home.databinding.ShareSearchLayoutBinding
import com.mument_android.home.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<ShareSearchLayoutBinding>(
    R.layout.share_search_layout,
    TransitionMode.HORIZONTAL
) {
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchListAdapter
    private lateinit var searchResultAdapter: SearchListAdapter
    @Inject
    lateinit var moveMusicDetailNavigatorProvider: MoveMusicDetailNavigatorProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingAdapterAndDatabinding()
        collectingList()
        addClickListener()
    }

    private fun addClickListener() {
        binding.ivBack.setOnClickListener { finish() }
        binding.etSearch.setOnEditorActionListener { edit, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callSearch()
            }
            false
        }
        binding.ivSearch.setOnClickListener {
            callSearch()
        }
        binding.etSearch.setOnFocusChangeListener { view, focused ->
            binding.ivDelete.visibility = if (focused) View.VISIBLE else View.GONE
        }

        binding.ivDelete.setOnClickListener {
            binding.etSearch.text = null
            viewmodel.setRecentData()
            binding.rcSearch.adapter = searchAdapter
            viewmodel.searchSwitch(false)
        }

        binding.etAllDelete.setOnClickListener {
            MumentDialogBuilder().setAllowListener {
                viewmodel.allListDelete()
            }.setCancelListener { }.setBody("").setHeader("최근 검색한 내역을\n모두 삭제하시겠어요?").build()
                .show(supportFragmentManager, "")
        }
    }

    private fun callSearch() {
        viewmodel.searchMusic(binding.etSearch.text.toString())
        binding.rcSearch.adapter = searchResultAdapter
    }

    private fun collectingList() {
        collectFlowWhenStarted(viewmodel.searchResultList) { result ->
            when (result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    viewmodel.searchSwitch(true)
                    searchResultAdapter.submitList(result.data)
                }
                else -> {}
            }
        }

        collectFlowWhenStarted(viewmodel.searchList) { result ->
            when (result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    searchAdapter.submitList(result.data)
                    binding.rcSearch.adapter = searchAdapter
                }
                else -> {}
            }
        }
    }

    private fun settingAdapterAndDatabinding() {
        binding.lifecycleOwner = this
        binding.viewmodel = viewmodel
        searchAdapter = SearchListAdapter(
            contentClickListener = { data ->
                viewmodel.selectContent(data)
                moveMusicDetailNavigatorProvider.intentMusicDetail(data._id)
            },
            itemClickListener = { data -> viewmodel.deleteRecentList(data) }
        )
        searchResultAdapter = SearchListAdapter(
            contentClickListener = { data ->
                viewmodel.selectContent(data)
                moveMusicDetailNavigatorProvider.intentMusicDetail(data._id)
            },
            itemClickListener = {}
        )
        binding.rcSearch.adapter = searchAdapter
    }
}