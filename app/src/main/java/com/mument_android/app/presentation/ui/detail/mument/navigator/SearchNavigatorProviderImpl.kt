package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import com.angdroid.navigation.SearchNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import javax.inject.Inject

class SearchNavigatorProviderImpl @Inject constructor(private val activity : Activity): SearchNavigatorProvider {
    override fun moveSearchFragment() {
        with(activity as MainActivity){
            this.navController.navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }
}