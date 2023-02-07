package com.mument_android.detail.report

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.detail.databinding.FragmentSelectReportTypeDialogBinding

class SelectReportTypeDialogFragment(): DialogFragment() {
    private var binding by AutoClearedValue<FragmentSelectReportTypeDialogBinding>()
    private var selectListener: ((String) -> Unit)? = null

    override fun onResume() {
        super.onResume()
        dialog?.window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSelectReportTypeDialogBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButtons()
    }

    fun attachSelectListener(listener: (String) -> Unit): SelectReportTypeDialogFragment {
        selectListener = listener
        return this
    }

    private fun clickButtons() {
        with(binding) {
            tvReportMument.setOnClickListener {
                selectListener?.let { it(SELECT_REPORT_MUMENT) }
                dismiss()
            }
            tvBlockUser.setOnClickListener {
                selectListener?.let { it(SELECT_BLOCK_USER) }
                dismiss()
            }
            tvCancel.setOnClickListener { dismiss() }
            root.setOnClickListener { dismiss() }
        }
    }

    companion object {
        const val SELECT_REPORT_MUMENT = "SELECT_REPORT_MUMENT"
        const val SELECT_BLOCK_USER = "SELECT_BLOCK_USER"

    }
}