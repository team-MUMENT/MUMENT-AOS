package com.mument_android.app.presentation.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.R
import com.mument_android.app.data.network.home.adapter.SearchListAdapter
import com.mument_android.app.presentation.ui.home.viewmodel.SearchViewModel
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentSearchBinding

class BottomSheetSearchFragment : BottomSheetDialogFragment() {
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchListAdapter
    private var binding by AutoClearedValue<FragmentSearchBinding>()

    companion object {
        @JvmStatic
        private var INSTANCE: BottomSheetSearchFragment? = null

        @JvmStatic
        fun newInstance(): BottomSheetSearchFragment {
            return INSTANCE ?: BottomSheetSearchFragment().apply {
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
        return R.style.BottomSheetDialog_Rounded
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            ((dialogInterface as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View).apply {
                val behavior = BottomSheetBehavior.from(this)
                val layoutParams = this.layoutParams
                behavior.disableShapeAnimations()
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                layoutParams.height = getBottomSheetDialogDefaultHeight()
                behavior.skipCollapsed = true
                this.layoutParams = layoutParams
            }
        }
        return dialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SearchListAdapter({}, {})
        binding.rcSearch.adapter = adapter
        binding.viewmodel = viewmodel
        adapter.submitList(viewmodel.searchList.value)
        binding.option = false

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