package com.mument_android.record

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.core_dependent.ext.launchWhenCreated
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.record.databinding.BottomsheetFragmentSearchBinding
import com.mument_android.record.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomSheetSearchFragment :
    BottomSheetDialogFragment() {
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchListAdapter
    private lateinit var searchResultAdapter: SearchListAdapter
    private val headerAdapter = BottomSearchHeaderAdapter()
    private lateinit var searchConcatAdapter: ConcatAdapter
    private var binding by AutoClearedValue<BottomsheetFragmentSearchBinding>()
    private lateinit var behavior: BottomSheetBehavior<View>

    companion object {
        @JvmStatic
        private var INSTANCE: BottomSheetSearchFragment? = null
        private lateinit var CONTENT_CLICK_CALLBACK: (RecentSearchData) -> Unit

        @JvmStatic
        fun newInstance(contentClick: (RecentSearchData) -> Unit): BottomSheetSearchFragment {
            return INSTANCE
                ?: BottomSheetSearchFragment().apply {
                    CONTENT_CLICK_CALLBACK = contentClick
                    INSTANCE = this
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = BottomsheetFragmentSearchBinding.inflate(inflater, container, false).run {
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.bottomViewmodel = viewmodel
        settingAdapterAndDatabinding()
        setListener()
        collectingList()
    }


    private fun callSearch() {
        viewmodel.searchMusic(binding.etSearch.text.toString())
        binding.rcSearch.adapter = searchResultAdapter
    }

    private fun settingAdapterAndDatabinding() {
        adapter = SearchListAdapter(contentClickListener = { data ->
            viewmodel.selectContent(data)
            CONTENT_CLICK_CALLBACK(data)
            dismiss()
        }, itemClickListener = { data ->
            viewmodel.deleteRecentList(data)
        })
        searchConcatAdapter = ConcatAdapter(headerAdapter, adapter)
        searchResultAdapter = SearchListAdapter(contentClickListener = { data ->
            viewmodel.selectContent(data)
            CONTENT_CLICK_CALLBACK(data)
            dismiss()
        }, itemClickListener = {})
        binding.rcSearch.adapter = searchConcatAdapter
    }

    private fun collectingList() {
        collectFlowWhenStarted(viewmodel.searchResultList) { result ->
            if (result != null) {
                searchResultAdapter.submitList(result)
                viewmodel.searchSwitch(true)
            }
        }
        collectFlowWhenStarted(viewmodel.searchList) { result ->
            adapter.submitList(result)
            binding.rcSearch.adapter = searchConcatAdapter
        }
    }

    private fun setListener() {
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

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 80 / 100
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val windowMetrics: WindowMetrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics((activity as AppCompatActivity))
        return windowMetrics.bounds.height()
    }

    override fun onStop() {
        super.onStop()
        viewmodel.searchText.value = ""
        viewmodel.searchSwitch(false)
        viewmodel.clearSearchResult()
    }
}


