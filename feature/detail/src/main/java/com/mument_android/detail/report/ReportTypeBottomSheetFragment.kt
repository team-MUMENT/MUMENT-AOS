package com.mument_android.detail.report

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.detail.databinding.FragmentReportTypeBottomSheetBinding

class ReportTypeBottomSheetFragment(): BottomSheetDialogFragment() {
    private var binding by AutoClearedValue<FragmentReportTypeBottomSheetBinding>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireActivity(), theme).also { dialog ->
            dialog.setOnShowListener {
                val parentLayout = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { bottomSheet ->
                    bottomSheet.background = null
                    setUpBottomSheetHeight(bottomSheet)
                    BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentReportTypeBottomSheetBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpBottomSheetHeight(bottomSheet: View) {
        val params = bottomSheet.layoutParams
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = params
    }
}