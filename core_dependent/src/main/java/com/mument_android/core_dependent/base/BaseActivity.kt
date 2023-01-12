package com.mument_android.core_dependent.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mument_android.core_dependent.R
import com.mument_android.core_dependent.util.TransitionMode

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    constructor(
        layoutResId: Int,
        mode: TransitionMode
    ) : this(layoutResId) {
        this.mode = mode
    }

    private var mode = TransitionMode.NONE
    private var _binding: T? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        when (mode) {
            TransitionMode.HORIZONTAL -> overridePendingTransition(
                R.anim.horizontal_enter,
                R.anim.none
            )
            TransitionMode.VERTICAL -> overridePendingTransition(
                R.anim.vertical_enter,
                R.anim.none
            )
            else -> Unit
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            when (mode) {
                TransitionMode.HORIZONTAL -> overridePendingTransition(
                    R.anim.none,
                    R.anim.horizontal_exit
                )
                TransitionMode.VERTICAL -> overridePendingTransition(
                    R.anim.none,
                    R.anim.vertical_exit
                )
                else -> Unit
            }
        }
    }

    override fun finish() {
        super.finish()
        when (mode) {
            TransitionMode.HORIZONTAL -> overridePendingTransition(
                R.anim.none,
                R.anim.horizontal_exit
            )
            TransitionMode.VERTICAL -> overridePendingTransition(
                R.anim.none,
                R.anim.vertical_exit
            )
            else -> Unit
        }
    }
}