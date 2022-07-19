package com.mument_android.app.presentation.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.R
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
<<<<<<< HEAD
import com.mument_android.app.data.network.util.ApiResult
=======
>>>>>>> 778d4b0 (Mument Dialog 수정)
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

<<<<<<< HEAD
class BottomSheetSearchFragment(private val contentClick: (RecentSearchData) -> Unit) :
    BottomSheetDialogFragment() {
    private val viewmodel: SearchViewModel by activityViewModels()
=======
class BottomSheetSearchFragment(private val contentClick: (RecentSearchData) -> Unit) : BottomSheetDialogFragment() {
    private val viewmodel: SearchViewModel by viewModels()
>>>>>>> 778d4b0 (Mument Dialog 수정)
    private lateinit var adapter: SearchListAdapter
    private var binding by AutoClearedValue<FragmentSearchBinding>()
    private lateinit var behavior: BottomSheetBehavior<View>

    companion object {
        @JvmStatic
        private var INSTANCE: BottomSheetSearchFragment? = null

        @JvmStatic
        fun newInstance(contentClick: (RecentSearchData) -> Unit): BottomSheetSearchFragment {
<<<<<<< HEAD
            return INSTANCE
                ?: BottomSheetSearchFragment(contentClick = { contentClick(it) }).apply {
                    INSTANCE = this
                }
=======
            return INSTANCE ?: BottomSheetSearchFragment(contentClick = { contentClick(it) }).apply {
                INSTANCE = this
            }
>>>>>>> 778d4b0 (Mument Dialog 수정)
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
        return R.style.BottomSheetDialog_Rounded
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener { dialogInterface ->
            ((dialogInterface as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View).apply {
                behavior = BottomSheetBehavior.from(this)
                val layoutParams = this.layoutParams
                behavior.disableShapeAnimations()
                layoutParams.height = getBottomSheetDialogDefaultHeight()
                behavior.skipCollapsed = true
                behavior.isHideable = true
                this.layoutParams = layoutParams
                behavior.state = BottomSheetBehavior.STATE_EXPANDED

            }
        }
        return dialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
<<<<<<< HEAD
        adapter = SearchListAdapter(requireContext(), { data ->
            viewmodel.selectContent(data)
        }, { data ->
            viewmodel.deleteRecentList(data)
=======
        adapter = SearchListAdapter(requireContext(),{
            contentClick(it)
            dismiss()
        }, {

>>>>>>> 778d4b0 (Mument Dialog 수정)
        })
        /*searchResultAdapter = SearchListAdapter(requireContext(),{ data ->
            viewmodel.selectContent(data)
        }, {})*/
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        binding.option = false
        binding.rcSearch.adapter = adapter

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

        binding.ivDelete.setOnClickListener {
            binding.etSearch.text = null
        }

        binding.etAllDelete.setOnClickListener {
            adapter.submitList(listOf())
            viewmodel.allListDelete()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        lifecycleScope.launch {
            viewmodel.searchList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {}
                        is ApiResult.Failure -> {}
                        is ApiResult.Success -> {
                            Timber.d("Bottom Collect ${result.data}")
                            adapter.submitList(result.data)
                        }
                    }
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


}