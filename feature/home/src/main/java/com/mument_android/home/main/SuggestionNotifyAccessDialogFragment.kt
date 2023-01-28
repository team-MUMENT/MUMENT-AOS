package com.mument_android.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.home.R
import com.mument_android.home.databinding.FragmentSuggestionNotifyAccessBinding

class SuggestionNotifyAccessDialogFragment(val result: (Boolean) -> Unit) :
    BottomSheetDialogFragment() {

    private var binding by AutoClearedValue<FragmentSuggestionNotifyAccessBinding>()

    companion object {
        @JvmStatic
        private var INSTANCE: SuggestionNotifyAccessDialogFragment? = null

        @JvmStatic
        fun newInstance(suggestionResult: (Boolean) -> Unit): SuggestionNotifyAccessDialogFragment {
            return INSTANCE
                ?: SuggestionNotifyAccessDialogFragment(result = suggestionResult).apply {
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
            result(true)
            dismiss()
        }
        binding.ivCancel.setOnClickListener {
            result(false)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            result(false)
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }
}