package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
import com.mument_android.app.domain.entity.SearchResultData
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

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
        searchAdapter = SearchListAdapter({ data ->
            viewmodel.selectContent(data)
        }, { data ->
            viewmodel.deleteRecentList(data)
        })
        searchResultAdapter = SearchListAdapter({ data ->
            viewmodel.selectContent(data)
        }, {})
        binding.rcSearch.adapter = searchAdapter
        searchAdapter.submitList(viewmodel.searchList.value)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        binding.option = true
        binding.etSearch.setOnClickListener {
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
        lifecycleScope.launch {
            viewmodel.searchResultList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it.size != 0) {
                        Timber.d("COLLECT!!!!!")
                        binding.rcSearch.adapter = searchResultAdapter
                        searchResultAdapter.submitList(it)
                        //searchAdapter.submitList(it)
                    } else {
                        Timber.d("Not COLLECT!!!!!")
                    }
                }
            viewmodel.searchContent.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                //Timber.d("collect!! $it")
            }
        }

        viewmodel.searchList.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it)
        }
    }

    override fun onStop() {
        super.onStop()
        viewmodel.searchResultList.value = listOf()
    }
}