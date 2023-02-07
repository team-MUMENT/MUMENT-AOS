package com.mument_android.app.presentation.ui.detail.mument.navigator

import androidx.navigation.NavController


fun NavController.isFragmentInBackStack(destinationId: Int): Boolean = try {
    getBackStackEntry(destinationId)
    true
} catch (e: Exception) {
    false
}