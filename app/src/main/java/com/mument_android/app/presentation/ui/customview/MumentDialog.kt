package com.mument_android.app.presentation.ui.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.mument_android.app.util.AutoClearedValue
import com.mument_android.app.util.ViewUtils.getDeviceSize
import com.mument_android.databinding.FragmentMumentDialogBinding

class MumentDialog(
    private val header: String?,
    private val body: String?,
    private val allowListener: (() -> Unit)?,
    private val cancelListener: (() -> Unit)?,
    private val option: Boolean
): DialogFragment() {
    private var binding by AutoClearedValue<FragmentMumentDialogBinding>()

    override fun onResume() {
        super.onResume()
        setDialogSize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMumentDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHeader.setContent(header)
        binding.tvBody.setContent(body)
        binding.tvAllow.text = if(option) "확인" else "삭제"
        setClickListener()
    }

    private fun setDialogSize() {
        val deviceSize = requireActivity().getDeviceSize()
        val deviceWidth = deviceSize[0]
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth*0.75).toInt()
        params?.height = (deviceWidth*0.75*0.65).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun TextView.setContent(content: String?) {
        if (content.isNullOrEmpty()) visibility = View.GONE else text = content
    }

    private fun setClickListener() {
        with(binding) {
            tvCancel.setOnClickListener {
                cancelListener?.let { it() }
                dismiss()
            }

            tvAllow.setOnClickListener {
                allowListener?.let { it() }
                dismiss()
            }
        }
    }
}