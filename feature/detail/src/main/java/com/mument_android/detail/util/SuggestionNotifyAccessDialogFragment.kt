package com.mument_android.detail.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.detail.R
import com.mument_android.detail.databinding.FragmentSuggestionNotifyAccessBinding

class SuggestionNotifyAccessDialogFragment :
    BottomSheetDialogFragment() {

    private var binding by AutoClearedValue<FragmentSuggestionNotifyAccessBinding>()

    companion object {
        @JvmStatic
        private var INSTANCE: SuggestionNotifyAccessDialogFragment? = null
        private lateinit var RESULT_CALLBACK: (Boolean) -> Unit

        @JvmStatic
        fun newInstance(suggestionResult: (Boolean) -> Unit): SuggestionNotifyAccessDialogFragment {
            return INSTANCE
                ?: SuggestionNotifyAccessDialogFragment().apply {
                    RESULT_CALLBACK = suggestionResult
                    INSTANCE = this
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSuggestionNotifyAccessBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnYes.setOnClickListener {
            RESULT_CALLBACK(true)
            dismiss()
        }
        binding.ivCancel.setOnClickListener {
            RESULT_CALLBACK(false)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            RESULT_CALLBACK(false)
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }
}