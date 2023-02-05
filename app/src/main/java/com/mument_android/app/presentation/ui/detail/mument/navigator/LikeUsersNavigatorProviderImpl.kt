package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import com.angdroid.navigation.LikeUsersNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.detail.mument.fragment.MumentLikeListFragment.Companion.USER_LIST_KEY
import com.mument_android.domain.entity.user.UserEntity
import com.mument_android.home.main.HomeFragment
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LikeUsersNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
): LikeUsersNavigatorProvider {
    override fun toLikeUsers(userList: ArrayList<Parcelable>) {
        with(activity as MainActivity) {
            Bundle().apply {
                putParcelableArrayList(USER_LIST_KEY, userList)
                navController.navigate(R.id.action_mumentDetailFragment_to_mumentLikeListFragment, this)
            }
        }
    }
}