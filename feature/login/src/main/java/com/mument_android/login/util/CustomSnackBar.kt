package com.mument_android.login.util

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.mument_android.login.R
import com.mument_android.login.databinding.CustomSnackbarBinding

class CustomSnackBar(view: View, private val message: String) {

    companion object {

        fun make(view: View, message: String) = CustomSnackBar(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 5000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: CustomSnackbarBinding = DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.tvSample.text = message
    }

    fun show() {
        snackbar.show()
    }
}