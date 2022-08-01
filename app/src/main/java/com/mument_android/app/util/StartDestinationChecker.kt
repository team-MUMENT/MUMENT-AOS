package com.mument_android.app.util

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mument_android.R

object StartDestinationChecker {
    fun Fragment.isFromHome(): Boolean = findNavController().graph.startDestinationId == R.id.homeFragment
}