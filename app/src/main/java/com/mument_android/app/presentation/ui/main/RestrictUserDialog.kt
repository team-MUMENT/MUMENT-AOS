package com.mument_android.app.presentation.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mument_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.databinding.AlretInapproUserBinding
import com.mument_android.record.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestrictUserDialog(context: Context) :
    DialogFragment() {

    val viewModel: MainViewModel by viewModels()
    private var binding by AutoClearedValue<AlretInapproUserBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = AlretInapproUserBinding.inflate(inflater, container, false).run {
        binding = this
        this.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataNetwork()
        clickListener()
    }

    @SuppressLint("ResourceType")
    override fun getTheme(): Int {
        return com.mument_android.R.drawable.rectangle_fill_white_11dp
    }

    private fun dataNetwork() {
        viewModel.limitUser.observe(viewLifecycleOwner) {
            binding.viewModel = it
        }
    }

    private fun clickListener() {
        binding.tvOk.setOnClickListener {
            dismiss()
        }
    }
}