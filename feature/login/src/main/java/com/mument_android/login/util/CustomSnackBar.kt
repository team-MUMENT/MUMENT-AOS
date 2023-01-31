package com.mument_android.login.util

import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.util.Size
import android.util.TypedValue
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
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
    private val snackbar = Snackbar.make(view, "", 2500)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: CustomSnackbarBinding = DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        val marginHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 107F, context.resources.displayMetrics)
        with(snackbarLayout) {
            removeAllViews()
            setPadding(20, marginHeight.toInt(), 20, 0)
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