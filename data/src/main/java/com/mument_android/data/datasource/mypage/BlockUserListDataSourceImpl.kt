package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class BlockUserListDataSourceImpl @Inject constructor(private val myPageApiService: MyPageApiService) :
    BlockUserListDataSource {
    override suspend fun fetchBlockUserList(
    ): List<BlockUserDto>?{
        return myPageApiService.fetchBlockUser().data
    }
}