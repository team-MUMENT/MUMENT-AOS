package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.os.Bundle
import com.angdroid.navigation.LikeUsersNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUMENT_ID
import javax.inject.Inject

class LikeUsersNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
) : LikeUsersNavigatorProvider {
    override fun toLikeUsers(mumentId: String) {
        with(activity as MainActivity) {
            Bundle().apply {
                putString(MUMENT_ID, mumentId)
                navController.navigate(
                    R.id.action_mumentDetailFragment_to_mumentLikeListFragment,
                    this
                )
            }
        }
    }
}