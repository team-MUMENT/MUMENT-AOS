package com.angdroid.navigation

import android.os.Parcelable
import com.mument_android.domain.entity.user.UserEntity

interface LikeUsersNavigatorProvider {
    fun toLikeUsers(userList: ArrayList<Parcelable>)
}