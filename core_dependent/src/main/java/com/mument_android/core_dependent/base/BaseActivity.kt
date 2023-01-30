package com.mument_android.core_dependent.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.mument_android.core_dependent.R
import com.mument_android.core_dependent.util.TransitionMode

abstract class BaseActivity<T : ViewDataBinding>(
    private val inflate: (LayoutInflater) -> T
) : AppCompatActivity() {
    constructor(
        inflate: (LayoutInflater) -> T,
        mode: TransitionMode
    ) : this(inflate) {
        this.mode = mode
    }

    private var mode = TransitionMode.NONE
    private val _binding: T? by lazy { inflate.invoke(layoutInflater) }
    val binding get() = _binding ?: throw NullPointerException("Binding is Null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
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