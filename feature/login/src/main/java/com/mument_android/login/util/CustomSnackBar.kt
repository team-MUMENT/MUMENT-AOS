package com.mument_android.login.util

import android.app.ActionBar
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
    private val snackbar = Snackbar.make(view, "", 2000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: CustomSnackbarBinding = DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, true)


    init {
        initView()
        initData()
        setLocation()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)

        }
    }

    private fun initData() {
        snackbarBinding.tvSample.text = message
    }

    private fun setLocation() {
        val snackBarView = snackbar.view
        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val marginHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 117F, context.resources.displayMetrics)
        params.setMargins(0, marginHeight.toInt(), 0, 0)

        snackBarView.layoutParams = params
    }

    fun show() {

        snackbar.show()
    }
}