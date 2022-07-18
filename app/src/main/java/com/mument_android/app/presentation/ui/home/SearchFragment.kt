package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentSearchBinding>()
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchListAdapter
    private lateinit var searchResultAdapter: SearchListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingAdapterAndDatabinding()
        collectingList()
        addClickListener()
    }

    private fun settingAdapterAndDatabinding() {
        searchAdapter = SearchListAdapter({ data ->
            viewmodel.selectContent(data)
        }, { data ->
            viewmodel.deleteRecentList(data)
        })
        searchResultAdapter = SearchListAdapter({ data ->
            viewmodel.selectContent(data)
        }, {})
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        binding.option = true
        binding.rcSearch.adapter = searchAdapter
    }

    private fun addClickListener() {

        binding.etSearch.setOnEditorActionListener { edit, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewmodel.searchMusic(binding.etSearch.text.toString())
            }
            Timber.d("done!! $actionId")
            false
        }
        binding.etSearch.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.ivDelete.visibility = View.VISIBLE
            } else {
                binding.ivDelete.visibility = View.GONE
            }
        }

        binding.etAllDelete.setOnClickListener {
            viewmodel.allListDelete()
        }
    }

    private fun collectingList() {
        viewmodel.searchResultList.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            if (result?.size != 0) {
                binding.rcSearch.adapter = searchResultAdapter
                searchResultAdapter.submitList(result)
                //searchAdapter.submitList(it)
            }
        }
        viewmodel.searchContent.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            //Timber.d("collect!! $it")
        }
        viewmodel.searchList.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            when (result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    searchAdapter.submitList(result.data)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewmodel.searchResultList.value = listOf()
    }
}