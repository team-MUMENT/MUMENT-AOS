package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.BlockUserDto

interface BlockUserListDataSource {
    suspend fun fetchBlockUserList() : List<BlockUserDto>?
}