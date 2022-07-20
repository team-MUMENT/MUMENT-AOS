package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.dto.detail.MumentDetailDto
import com.mument_android.app.data.dto.UserDto
import com.mument_android.app.data.network.base.BaseResponse
import com.mument_android.app.data.network.detail.DetailApiService
import javax.inject.Inject

/**
 * Todo: 서버 Instance 올라가면 api 연결하고, 더미데이터 삭제하기
 */

class MumentDetailDataSourceImpl @Inject constructor(
    private val mumentDetailApiService: DetailApiService
): MumentDetailDataSource {
    override suspend fun fetchMumentDetail(mumentId: String, userId: String): BaseResponse<MumentDetailDto> {
        return mumentDetailApiService.fetchMumentDetail(mumentId, userId)
//        return MumentDetailDto(
//            content = "음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요. 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게음악은 저에게 영감을 줘요, 이 곡 추천해준 이부장에게 심심한 감사의 인사를 음악은 저에게 영감을 줘요.",
//            count = 3,
//            createdAt = "26 Sep, 2022",
//            isFirst = true,
//            feelingTag = listOf(3,5,6),
//            impressionTag = listOf(3,6),
//            isLiked = true,
//            likeCount = 57,
//            music = MusicDto("musicId", "(여자)아이들", "https://img3.yna.co.kr/etc/inner/KR/2022/03/20/AKR20220320029100005_01_i_P4.jpg", "TOMBOY"),
//            user = UserDto("userId", "https://img3.yna.co.kr/etc/inner/KR/2022/03/20/AKR20220320029100005_01_i_P4.jpg", "손평화"),
//        )
    }
    // = mumentDetailApiService.fetchMumentDetail(mumentId, userId)
}