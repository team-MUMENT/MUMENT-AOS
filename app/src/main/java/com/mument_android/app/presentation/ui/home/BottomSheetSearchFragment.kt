package com.mument_android.app.presentation.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.R
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.launchWhenCreated
import com.mument_android.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomSheetSearchFragment(private val contentClick: (RecentSearchData) -> Unit) :
    BottomSheetDialogFragment() {
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchListAdapter
    private lateinit var searchResultAdapter: SearchListAdapter
    private var binding by AutoClearedValue<FragmentSearchBinding>()
    private lateinit var behavior: BottomSheetBehavior<View>

    companion object {
        @JvmStatic
        private var INSTANCE: BottomSheetSearchFragment? = null

        @JvmStatic
        fun newInstance(contentClick: (RecentSearchData) -> Unit): BottomSheetSearchFragment {
            return INSTANCE
                ?: BottomSheetSearchFragment(contentClick = { contentClick(it) }).apply {
                    INSTANCE = this
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener { dialogInterface ->
            ((dialogInterface as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View).apply {
                behavior = BottomSheetBehavior.from(this)
                val layoutParams = this.layoutParams
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                layoutParams.height = getBottomSheetDialogDefaultHeight()
                behavior.disableShapeAnimations()
                behavior.skipCollapsed = true
                behavior.isHideable = true
                this.layoutParams = layoutParams
            }
        }
        return dialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingAdapterAndDatabinding()
        setListener()
        collectingList()
    }

    override fun onResume() {
        super.onResume()
        viewmodel.searchText.value = ""
        viewmodel.setRecentData(lifecycleScope)
    }

    private fun settingAdapterAndDatabinding() {
        adapter = SearchListAdapter({ data ->
            viewmodel.selectContent(data)
            viewmodel.setRecentData(lifecycleScope)
            contentClick(data)
            dismiss()
        }, { data ->
            viewmodel.deleteRecentList(data)
        })
        adapter.option = true
        searchResultAdapter = SearchListAdapter({ data ->
            viewmodel.selectContent(data)
            viewmodel.setRecentData(lifecycleScope)
            contentClick(data)
            binding.searchOption = false
            dismiss()
        }, {})
        searchResultAdapter.option = false
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        binding.option = false
        binding.rcSearch.adapter = adapter
    }

    private fun collectingList() {
        viewmodel.searchResultList.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            when (result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    searchResultAdapter.submitList(result.data)
                    viewmodel.searchText.value = binding.etSearch.text.toString()
                }
            }
        }
        viewmodel.searchList.launchWhenCreated(viewLifecycleOwner.lifecycleScope) { result ->
            when (result) {
                is ApiResult.Loading -> {}
                is ApiResult.Failure -> {}
                is ApiResult.Success -> {
                    adapter.submitList(result.data)
                    binding.rcSearch.adapter = adapter
                }
            }
        }
    }

    private fun setListener() {
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
                searchResultAdapter.submitList(listOf())
                binding.rcSearch.adapter = adapter
            }
        }


    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 80 / 100
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val windowMetrics: WindowMetrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics((activity as MainActivity))
        val pxHeight = windowMetrics.bounds.height()
        return pxHeight
    }

    override fun onStop() {
        super.onStop()
        viewmodel.searchResultList.value?.data?.toMutableList()?.clear()
    }
}


