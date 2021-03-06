package com.mument_android.app.presentation.ui.detail.mument

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.databinding.FragmentEditMumentDialogBinding

class EditMumentDialogFragment(private val editListener: EditListener): DialogFragment() {
    interface EditListener {
        fun edit()
        fun delete()
    }

    private var binding by AutoClearedValue<FragmentEditMumentDialogBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEditMumentDialogBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvCancel.setOnClickListener { dismiss() }
            root.setOnClickListener { dismiss() }
            tvEdit.setOnClickListener {
                editListener.edit()
                dismiss()
            }
            tvDelete.setOnClickListener {
                editListener.delete()
                dismiss()
            }

        }
    }
}