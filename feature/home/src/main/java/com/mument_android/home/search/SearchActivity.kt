package com.mument_android.home.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.angdroid.navigation.MainHomeNavigatorProvider
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.core_dependent.util.TransitionMode
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.home.adapters.SearchHeaderAdapter
import com.mument_android.home.adapters.SearchListAdapter
import com.mument_android.home.databinding.ShareSearchLayoutBinding
import com.mument_android.home.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<ShareSearchLayoutBinding>(
    inflate = ShareSearchLayoutBinding::inflate,
    mode = TransitionMode.HORIZONTAL
) {
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchListAdapter
    private val headerAdapter = SearchHeaderAdapter(::allDelete)
    private lateinit var searchConcatAdapter: ConcatAdapter
    private lateinit var searchResultAdapter: SearchListAdapter
    @Inject
    lateinit var mainHomeNavigatorProvider: MainHomeNavigatorProvider

    override fun onBackPressed() {
        super.onBackPressed()

    }

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
            if (viewmodel.searchOption.value) {
                binding.rcSearch.adapter = searchConcatAdapter
                viewmodel.searchSwitch(false)
            }
        }
    }

    private fun allDelete() {
        MumentDialogBuilder().setAllowListener {
            viewmodel.allListDelete()
        }.setCancelListener { }.setHeader("최근 검색한 내역을\n모두 삭제하시겠어요?").build()
            .show(supportFragmentManager, "")
    }

    private fun callSearch() {
        viewmodel.searchMusic(binding.etSearch.text.toString())
        binding.rcSearch.adapter = searchResultAdapter
    }

    private fun collectingList() {
        collectFlowWhenStarted(viewmodel.searchResultList) { result ->
            if (result != null) {
                searchResultAdapter.submitList(result)
            }
        }

        collectFlowWhenStarted(viewmodel.searchList) { result ->
            searchAdapter.submitList(result)
            binding.rcSearch.adapter = searchConcatAdapter
        }
    }

    private fun settingAdapterAndDatabinding() {
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        searchAdapter = SearchListAdapter(
            contentClickListener = { data ->
                viewmodel.selectContent(data)
                selectMusic(data)
            },
            itemClickListener = { data -> viewmodel.deleteRecentList(data) }
        )
        searchConcatAdapter = ConcatAdapter(headerAdapter, searchAdapter)
        searchResultAdapter = SearchListAdapter(
            contentClickListener = { data ->
                viewmodel.selectContent(data)
                selectMusic(data)
            },
            itemClickListener = {}
        )
        binding.rcSearch.adapter = searchConcatAdapter
    }

    private fun selectMusic(data: RecentSearchData) {
        val music = MusicInfoEntity(data._id, data.name, data.image, data.artist)
        mainHomeNavigatorProvider.searchToMain(music)
//        val intent = Intent().apply {
//            putExtra(START_NAV_KEY, FROM_SEARCH)
//            putExtra(MUSIC_INFO_ENTITY, music)
//        }
//        setResult(RESULT_OK, intent)
//        finish()
    }

}