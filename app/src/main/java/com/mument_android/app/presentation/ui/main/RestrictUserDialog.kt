package com.mument_android.app.presentation.ui.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mument_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.databinding.AlretInapproUserBinding
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
        setSize()
        dataNetwork()
        clickListener()
    }

    private fun setSize() {
        val width = resources.getDimensionPixelSize(com.mument_android.R.dimen.design_alert_width)
        val height = resources.getDimensionPixelSize(com.mument_android.R.dimen.design_alert_height)
        dialog!!.window!!.setLayout(width, height)
    }

    override fun getTheme(): Int {
        return com.mument_android.core_dependent.R.style.DialogTheme
    }

    private fun dataNetwork() {
        viewModel.limitUser()
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