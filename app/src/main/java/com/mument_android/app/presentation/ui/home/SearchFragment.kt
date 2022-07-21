package com.mument_android.app.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.customview.MumentDialogBuilder
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentSearchBinding>()
    private val viewmodel: SearchViewModel by activityViewModels()
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
        searchAdapter = SearchListAdapter(requireContext(), { data ->
            viewmodel.selectContent(data)
        }, { data ->
            viewmodel.deleteRecentList(data)
        })
        searchAdapter.option = true
        searchResultAdapter = SearchListAdapter(requireContext(), { data ->
            viewmodel.selectContent(data)
        }, {})
        viewmodel.setRecentData(lifecycleScope)
        searchResultAdapter.option = false
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        binding.option = true
        binding.rcSearch.adapter = searchAdapter
    }

    private fun addClickListener() {

        binding.etSearch.setOnEditorActionListener { edit, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewmodel.searchMusic(binding.etSearch.text.toString())
                binding.rcSearch.adapter = searchResultAdapter
                binding.searchOption = true
            }
            false
        }
        binding.etSearch.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.ivDelete.visibility = View.VISIBLE
            } else {
                binding.ivDelete.visibility = View.GONE
            }
        }

        binding.ivDelete.setOnClickListener {
            binding.etSearch.text = null
            lifecycleScope.launch {
                viewmodel.setRecentData(this)
                binding.searchOption = false
                binding.rcSearch.adapter = searchAdapter
            }
        }

        binding.etAllDelete.setOnClickListener {
            MumentDialogBuilder().setAllowListener {
                viewmodel.allListDelete()
            }.setCancelListener { }.setBody("").setHeader("최근 검색한 내역을\n모두 삭제하시겠어요?").build()
                .show(childFragmentManager, this.tag)
        }
    }

    private fun collectingList() {
        viewmodel.searchResultList.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            when (result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    if (result.data!!.isNotEmpty()) {
                        searchResultAdapter.submitList(result.data)
                    }
                }
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
                    binding.rcSearch.adapter = searchAdapter
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewmodel.searchResultList.value?.data?.toMutableList()?.clear()
    }
}
