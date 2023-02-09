package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mument_android.R


fun NavController.isFragmentInBackStack(destinationId: Int): Boolean = try {
    getBackStackEntry(destinationId)
    true
} catch (e: Exception) {
    false
}

fun AppCompatActivity.checkCurrentFragment(): Fragment {
    val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
    return navHost.childFragmentManager.fragments.get(0)
}