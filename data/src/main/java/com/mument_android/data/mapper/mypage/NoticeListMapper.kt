package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.domain.entity.mypage.NoticeListEntity

class NoticeListMapper : BaseMapper<List<NoticeListDto>, List<NoticeListEntity>> {
    override fun map(from: List<NoticeListDto>): List<NoticeListEntity> =
        from.map { element ->
            NoticeListEntity(
                element.title,
                element.createAt
            )
        }
}