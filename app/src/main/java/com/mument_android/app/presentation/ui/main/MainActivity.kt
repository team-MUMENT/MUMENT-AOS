package com.mument_android.app.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mument_android.R
import com.mument_android.app.domain.entity.EmotionalTag
import com.mument_android.app.presentation.base.BaseActivity
import com.mument_android.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        Timber.e("${EmotionalTag.findEmotionalType(10)}")
        EmotionalTag
    }

    private fun initNavigation() {
        NavigationUI.setupWithNavController(binding.navBar, findNavController(R.id.nav_host))
    }
}